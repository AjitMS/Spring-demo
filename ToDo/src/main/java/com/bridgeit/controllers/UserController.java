package com.bridgeit.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;
import com.bridgeit.entity.UserLoginPair;
import com.bridgeit.service.UserService;
import com.bridgeit.tokenAuthentication.TokenGenerator;
import com.bridgeit.utilities.Encryption;

@RestController("/")
public class UserController {

	Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	UserService userService;

	User user;

	@Autowired
	TokenGenerator generator;
	@Autowired
	Encryption encryption;

	@GetMapping("/")
	public ResponseEntity<String> welcomeUser() {
		String welcome = "**Welcome to ToDo App**<br> use /login to login, \t<br> use /register to register,<br> \n use /fbconnect to login social<br>";
		return new ResponseEntity<String>(welcome, HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginPair loginPair, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		
		logger.info("Only an activated user can log in");
		
		logger.info("Into Login");
		logger.info("loign pair is: "+ loginPair);
		String email = loginPair.getEmail();
		String password = loginPair.getPassword();
		// grab entire user by email if proper credentials
		user = userService.getUserByEmail(email, user);
		if (!user.getIsValid())
			return new ResponseEntity<String>("Account not validated. please check email or register", HttpStatus.FORBIDDEN);
		if (userService.loginUser(email, password)) {
			System.out.println("login success");
			user = userService.getUserByEmail(email, user);

			// TokenGenerator generator = new TokenGenerator();

			// stop making objects. instead use @Autowired
			// make sure user prototype type of @Autowired instead of singleton
			// mistakenly @Autowired token and it returned the same token always for both
			// refresh and access

			Token accessToken = generator.generateTokenAndPushIntoRedis(user.getId(), "accesstoken");
			Token refreshToken = generator.generateTokenAndPushIntoRedis(user.getId(), "refreshtoken");
			List<Token> tokenList = new ArrayList<>();
			tokenList.add(accessToken);
			tokenList.add(refreshToken);
			// generate token for specific user id and store it in REDIS

			logger.info("ACCESS TOKEN: "+ accessToken);
			logger.info("REFRESH TOKEN: "+ refreshToken);
			logger.info("Token List is "+ tokenList);

			// send token link to user email
			userService.sendLoginVerificationToken(user, accessToken, request);
			logger.info("Email has been sent to  "+ user.getEmail() + " .please check");
			return new ResponseEntity<String>(
					"Access Token: " + accessToken + "<br>Refresh Token: " + refreshToken + " <br>please check mail",
					HttpStatus.OK);

		}
		logger.error("Login failed");
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/login/{userId}/{tokenId}")
	public ResponseEntity<String> verifyUserToken(@PathVariable("userId") Integer userId,
			@PathVariable("tokenId") String userTokenId) {

		// first validate access token is intact, if yes login

		if (generator.verifyUserToken(userId, userTokenId, "accessToken")) {
			logger.info("Congratulations ! Access Token validation sucess");
			return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
		} else {
			// else validate refresh token

			logger.error("Access token validation failed, starting refresh token validation");
			if (generator.verifyUserToken(userId, userTokenId, "refreshToken")) {

				// and generate another new access token and login
				Token newToken = generator.generateTokenAndPushIntoRedis(userId, "accessToken");

				logger.info("New access token is generated as: +"+ newToken + " for user " + user.getId());
				return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
			}
			// if refresh token fails, login fails
			else
				System.out.println("Refresh token validation failed");
			return new ResponseEntity<String>("Token not authenticated !", HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<String> forgotPassword(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		try {
			user = userService.getUserByEmail(user.getEmail(), user);
			logger.info("email is: " + user.getEmail());
			logger.info("user is: " + user);
		} catch (Exception E) {
			E.printStackTrace();
		}
		if (user == null) {
			logger.debug("No such email registered");
			return new ResponseEntity<String>("No such email registered", HttpStatus.NO_CONTENT);
		}
		// generating user token for forgot password
		// generator is autowired
		String tokenType = "forgottoken";
		Token token = generator.generateTokenAndPushIntoRedis(user.getId(), tokenType);
		userService.sendResetPasswordMail(user, request, token);

		return new ResponseEntity<String>("reset password link has been sent to " + user.getEmail(),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/resetpasswordtoken/{userId}/{userTokenId}")
	public ResponseEntity<String> validateResetPasswordToken(@PathVariable("userId") Integer userId,
			@PathVariable("userTokenId") String userTokenId) {
		if (generator.verifyUserToken(userId, userTokenId, "forgotToken"))
			return new ResponseEntity<String>("Redirecting to the password resetting page..", HttpStatus.OK);
		return new ResponseEntity<String>("Invalid link or incorrect token. password resetting failed. try again",
				HttpStatus.NO_CONTENT);
	}

	@PostMapping("forgotpassword/resetpassword")
	public ResponseEntity<String> resetPassword(@RequestBody User user) {

		if (user.getPassword().equals(user.getConfirmPassword())) {
			userService.resetPassword(user.getEmail(), user.getPassword());
			return new ResponseEntity<String>("Success ! proceding to Log In...", HttpStatus.OK);

		}
		return new ResponseEntity<>("passwords do not match", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid User user, BindingResult bindingResult)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		System.out.println("WOOHOO !");
		if (bindingResult.hasErrors()) {
			logger.info("Errors are: " + bindingResult);
			logger.info("User details: " + user);
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		logger.info("User details: "+ user);

		// saving user if not exists
		if (!userService.userExists(user)) {
			userService.registerUser(user);
			logger.info("Register Success");
		} else {
			return new ResponseEntity<String>("User Exists, please login. or forgot password ?",
					HttpStatus.NOT_ACCEPTABLE);
		}

		// Email verification
		userService.sendRegistrationVerificationLink(user.getId(), user.getEmail());

		String greeting = "Thank you! \n A verification email has been sent to " + user.getEmail()
				+ ". confirm registration by accessing link in the mail";

		return new ResponseEntity<String>(greeting, HttpStatus.OK);
	}

	@GetMapping("/register/activateuser/{id}")
	public ResponseEntity<String> activateUser(@PathVariable("id") Integer id) {
		userService.activateUser(id);
		logger.info("User verified successfully !");
		return new ResponseEntity<String>("Verified Successfully ! Redirecting to homepage...", HttpStatus.ACCEPTED);

	}
}