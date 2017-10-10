package com.bridgeit.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;
import com.bridgeit.service.UserService;

@RestController("/register")
public class RegisterController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid User user, BindingResult bindingResult) {
		System.out.println("WOOHOO !");
		if (bindingResult.hasErrors()) {
			System.out.println("Errors are: " + bindingResult);
			System.out.println("User details: " + user);
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		System.out.println("User details: " + user);

		// saving user if not exists
		if (!userService.userExists(user)) {
			userService.registerUser(user);
			System.out.println("Register Success");
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

	@GetMapping("/register/verifyuser/{id}")
	public ResponseEntity<String> verifyRegisteredUser(@PathVariable("id") String id) {
		userService.validateRegisteredUser(id);
		System.out.println("User verified successfully !");
		return new ResponseEntity<String>("Verified Successfully ! Redirecting to homepage...", HttpStatus.ACCEPTED);

	}

}
