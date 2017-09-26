package com.luv2code.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringHelloApp {

	public static void main(String[] args) {

		// load Spring configuration file
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		// retrieve bean from container
		Coach theCoach = context.getBean("myCoach", BaseballCoach.class);

		// call methods from bean
		System.out.println(theCoach.getDailyWorkout());

		System.out.println(theCoach.getFortune());
		// close the context
		context.close();

	}

}
