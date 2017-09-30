package com.springmvc.controllerannotations;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
things needed without annotations are web.xml,
 dispatcher-servlet.xml, controller.
*/

@Controller
@RequestMapping("/")
public class HelloWorldControllerAnnotation {

	@RequestMapping(method = RequestMethod.GET) // no path specifying
	public String sayHello(ModelMap model) {
		model.addAttribute("greeting", "Hello from sayHello()");
		System.out.println("Hello from sayHello()");
		return "welcome";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/sayhelloagain")
	public String sayHelloAgain(ModelMap model) {
		model.addAttribute("greeting", "Hello from sayHelloAgain()");
		System.out.println("Hello from sayHelloAgain()");
		return "welcome";
	}

}
