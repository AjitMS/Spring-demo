package com.bridgeit.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.beans.Message;

@RestController
public class HelloWorldController {

	Message msg;
	@RequestMapping("/")
	public String Welcome() {
		return "Welcome to REST Page";
	}

	@RequestMapping("/hello/{player}")
	public Message message(@PathVariable String player) {
		msg = new Message(player, "Hello " + player + " !");
		return msg;
	}
}
