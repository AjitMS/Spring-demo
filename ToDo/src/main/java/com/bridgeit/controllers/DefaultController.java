package com.bridgeit.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class DefaultController {
	@GetMapping("/")
	public ResponseEntity<String> greeting() { 
		return new ResponseEntity<String>(
				"Project running Successfully<br> 1. use \"/login\" to login and <br> 2. \"/register\" to register",
				HttpStatus.ACCEPTED);
	}	
}
