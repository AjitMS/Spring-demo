package com.bridgeit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;
import com.bridgeit.service.UserService;
import com.bridgeit.tokenAuthentication.Token;
import com.bridgeit.tokenAuthentication.TokenGenerator;

@RestController("/login")
public class LoginController {

	@Autowired
	UserService userService;
	User user;

	@GetMapping("/login")
	public ResponseEntity<String> loginUser(String email, String password) {
		System.out.println("Into Login");
		try {
			user = userService.getUserByEmail(email, user);
			user.setPassword(password);
		} catch (Exception E) {
			System.out.println("Empty Credentials");
			return new ResponseEntity<String>("Login Failure", HttpStatus.NO_CONTENT);
		}

		if (userService.loginUser(user)) {
			TokenGenerator generator = new TokenGenerator();
			Token token = generator.generateToken();
			userService.verifyLoggedInUser(user, token);
			return new ResponseEntity<String>("Login Token Sent. check Email", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Login Failure", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/login/{id}/{tokenId}")
	public ResponseEntity<String> redirect() {
		System.out.println("Congratulations !");
		
		return new ResponseEntity<String>("Token authenticated !", HttpStatus.ACCEPTED);
	}

}
