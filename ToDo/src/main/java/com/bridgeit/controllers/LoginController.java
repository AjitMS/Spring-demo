package com.bridgeit.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController("/login")
public class LoginController {

	@Autowired
	UserService userService;
	User user;
	@Autowired
	TokenGenerator generator;

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginPair loginPair, HttpServletRequest request) {
		System.out.println("Into Login");
		System.out.println("loign pair is: "+loginPair);
		String email = loginPair.getEmail();
		String password = loginPair.getPassword();
		// grab entire user by email if proper credentials

		if (userService.loginUser(email, password)) {
			System.out.println("loginn success");
			user = userService.getUserByEmail(email, user);

			// TokenGenerator generator = new TokenGenerator();

			// stop making objects. instead use @Autowired

			// generate token for specific user id and store it in REDIS

			Token token = generator.generateToken(user);
			generator.pushIntoRedis(user, token, "accesstoken");

			// send token link to user email
			
			userService.sendLoginVerificationToken(user, token, request);
			return new ResponseEntity<String>("Login Token Sent. check Email", HttpStatus.OK);
		}
		System.out.println("Login failed");
		return new ResponseEntity<String>("Login Failure", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/login/{userId}/{tokenId}")
	public ResponseEntity<String> verifyUserToken(@PathVariable("userId") Integer userId,
			@PathVariable("tokenId") String userTokenId) {
		if (generator.verifyUserToken(userId, userTokenId)) {
			System.out.println("Congratulations !");
			return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
		} else
			return new ResponseEntity<String>("Token not authenticated !", HttpStatus.NO_CONTENT);

	}

	@PostMapping("/login/forgotpassword")
	public ResponseEntity<String> forgotPassword(@RequestBody User user, HttpServletRequest request) {
		try {
			user = userService.getUserByEmail(user.getEmail(), user);
			System.out.println("email is: " + user.getEmail());
			System.out.println("user is: " + user);
		} catch (Exception E) {
			E.printStackTrace();
		}
		if (user == null) {
			System.out.println("No such email registered");
			return new ResponseEntity<String>("No such email registered", HttpStatus.NO_CONTENT);
		}
		// generating user token for forgot password
		// generator is autowired
		Token token = generator.generateToken(user);
		userService.sendResetPasswordMail(user, request, token);

		return new ResponseEntity<String>("reset password link has been sent to " + user.getEmail(),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/login/resetpasswordtoken/{userId}/{userTokenId}")
	public ResponseEntity<String> validateResetPasswordToken(@PathVariable("userId") Integer userId,
			@PathVariable("userTokenId") String userTokenId) {
		if (generator.verifyUserToken(userId, userTokenId))
			return new ResponseEntity<String>("Redirecting to the password resetting page..",
					HttpStatus.OK);
		return new ResponseEntity<String>("Invalid link or incorrect token. password resetting failed. try again", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/login/resetpassword")
	public ResponseEntity<String> resetPassword(@RequestBody User user) {

		if (user.getPassword().equals(user.getConfirmPassword())) {
			userService.resetPassword(user.getEmail(), user.getPassword());
			return new ResponseEntity<String>("Success ! proceding to Log In...", HttpStatus.OK);

		}
		return new ResponseEntity<>("passwords do not match", HttpStatus.NO_CONTENT);
	}

}
