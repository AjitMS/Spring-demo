package com.bridgeit.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;
import com.bridgeit.service.UserService;

@RestController("/")
public class RegisterController {

	@Autowired
	UserService userService;

	@GetMapping("/")
	public ResponseEntity<String> registerUser(@Valid User user, BindingResult bindingResult) {
		System.out.println("WOOHOO !");
		if (bindingResult.hasErrors()) {
			System.out.println("Errors are: " + bindingResult);
			System.out.println("User details: " + user);
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		System.out.println("User details: " + user);

		// saving user

			userService.registerUser(user);
		System.out.println("Register Success");

		// Email verification

		userService.sendVerificationLink(user.getId(), user.getEmail());

		String greeting = "Thank you! \n A verification email has been sent to " + user.getEmail()
				+ ". confirm registration by accessing link in the mail";

		return new ResponseEntity<String>(greeting, HttpStatus.OK);
	}
}
