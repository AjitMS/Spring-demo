package com.bridgeit.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.emailUtility.EmailVerification;
import com.bridgeit.entity.User;

@RestController("/")
public class RegisterController {
	@Autowired
	EmailVerification verifyEmail;

	@GetMapping("/")
	public ResponseEntity<String> registerUser(@Valid User user, BindingResult bindingResult) {
		System.out.println("WOOHOO !");
		if (bindingResult.hasErrors()) {
			System.out.println("Errors are: " + bindingResult);
			System.out.println("User details: " + user);
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		System.out.println("User details: " + user);
		System.out.println("Register Success");
		
		String link = "http://localhost:8080/ToDo/homepage";
		// Email verification
		verifyEmail.sendMail("bridgeit@gmail.com", "ajitshikalgar786@gmail.com", "Successful Registration",
				link);
		String greeting = "Thank you! \n A verification email has been sent to " + user.getEmail()
				+ ". confirm by accessing link in the mail";
		return new ResponseEntity<String>(greeting, HttpStatus.OK);
	}
}
