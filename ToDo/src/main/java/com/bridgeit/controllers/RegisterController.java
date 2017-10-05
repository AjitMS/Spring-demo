package com.bridgeit.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;

@RestController("/")
public class RegisterController {

	@GetMapping("/")
	public ResponseEntity<String> registerUser(@Valid User user, BindingResult bindingResult) {
		System.out.println("WOOHOO !");
		if (bindingResult.hasErrors()) {
			System.out.println("Errors are: " + bindingResult);
			System.out.println("User details: "+user);
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		System.out.println("User details: "+user);
		System.out.println("Register Success");
		return new ResponseEntity<String>("Registration Success !!!", HttpStatus.OK);
	}
}
	//email linking