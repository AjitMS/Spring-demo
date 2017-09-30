package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
things needed without annotations are web.xml,
 dispatcher-servlet.xml, controller.
*/

@Controller
@RequestMapping(value = "/")
public class HelloWorldController {

	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(ModelMap model) {
		System.out.println("Hello User !");
		model.addAttribute("greeting", "Hello from Spring MVC");
		return "welcome";
	}

	@RequestMapping(value = "/sayhelloagain", method = RequestMethod.GET)
	public String sayHelloAgain(ModelMap model) {
		model.addAttribute("greeting", "Hello Again from Spring MVC");
		System.out.println("Hello Again User !");
		return "welcome";
	}

}
