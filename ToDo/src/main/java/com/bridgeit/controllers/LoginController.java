package com.bridgeit.controllers;

import java.util.ArrayList;
import java.util.List;

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
	public ResponseEntity<List<Token>> loginUser(@RequestBody UserLoginPair loginPair, HttpServletRequest request) {
		System.out.println("Into Login");
		System.out.println("loign pair is: " + loginPair);
		String email = loginPair.getEmail();
		String password = loginPair.getPassword();
		// grab entire user by email if proper credentials
		if (userService.loginUser(email, password)) {
			System.out.println("login success");
			user = userService.getUserByEmail(email, user);

			// TokenGenerator generator = new TokenGenerator();

			// stop making objects. instead use @Autowired
			Token accessToken = generator.generateTokenAndPushIntoRedis(user.getId(), "accesstoken");
			Token refreshToken = generator.generateTokenAndPushIntoRedis(user.getId(), "refreshtoken");
			List<Token> tokenList = new ArrayList<>();
			tokenList.add(accessToken);
			tokenList.add(refreshToken);
			// generate token for specific user id and store it in REDIS

			System.out.println("ACCESS TOKEN: " + accessToken);
			userService.sendLoginVerificationToken(user, accessToken, request);

			System.out.println("REFRESH TOKEN: " + refreshToken);

			System.out.println("Token List " + tokenList);

			// send token link to user email

			return new ResponseEntity<List<Token>>(tokenList, HttpStatus.OK);
		}
		System.out.println("Login failed");
		return new ResponseEntity<List<Token>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/login/{userId}/{tokenId}")
	public ResponseEntity<String> verifyUserToken(@PathVariable("userId") Integer userId,
			@PathVariable("tokenId") String userTokenId) {

		// first validate access token is intact, if yes login

		if (generator.verifyUserToken(userId, userTokenId, "accessToken")) {
			System.out.println("Congratulations ! Access Token validation sucess");
			return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
		} else {
			// else validate refresh token
			System.out.println("Access token validation failed");
			if (generator.verifyUserToken(userId, userTokenId, "refreshToken")) {

				// and generate another new access token and login
				Token newToken = generator.generateTokenAndPushIntoRedis(userId, "accessToken");

				System.out.println("New access token is generated as: " + newToken);
				return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
			}
			// if refresh token fails, login fails
			else
				System.out.println("Refresh token validationn failed");
			return new ResponseEntity<String>("Token not authenticated !", HttpStatus.NO_CONTENT);
		}

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
		// generating a token for forgot password
		String tokenType = "acesstoken";
		Token token = generator.generateTokenAndPushIntoRedis(user.getId(), tokenType);
		userService.sendResetPasswordMail(user, request, token);

		return new ResponseEntity<String>("reset password link has been sent to " + user.getEmail(),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/login/resetpasswordtoken/{userId}/{userTokenId}")
	public ResponseEntity<String> validateResetPasswordToken(@PathVariable("userId") Integer userId,
			@PathVariable("userTokenId") String userTokenId) {
		if (generator.verifyUserToken(userId, userTokenId, "forgotToken"))
			return new ResponseEntity<String>("Redirecting to the password resetting page..", HttpStatus.OK);
		return new ResponseEntity<String>("Invalid link or incorrect token. password resetting failed. try again",
				HttpStatus.NO_CONTENT);
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
