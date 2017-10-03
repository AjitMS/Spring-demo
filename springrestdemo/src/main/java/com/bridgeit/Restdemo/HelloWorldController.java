package com.bridgeit.Restdemo;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloWorldController {

	@RequestMapping("/")
	public String Welcome() {
		return "Welcome to REST Page";
	}
	
	@RequestMapping("/hello/{player}")
	public Message message(@PathVariable String player) {
		Message msg = new Message(player, "Hello "+player+" !");
		return msg;	
	}
}
