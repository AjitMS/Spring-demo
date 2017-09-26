package com.luv2code.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterCricketDemo {

	public static void main(String[] args) {

		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CricketCoach theCoach  = context.getBean("cricketCoach", CricketCoach.class);
		System.out.println("Cricket Workout: "+theCoach.getDailyWorkout());
		System.out.println("Daily Fortune: "+theCoach.getFortune());
		context.close();
	}

}
