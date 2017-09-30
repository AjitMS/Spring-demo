package com.sample.project;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class DriverApp {

	public static void main(String[] args) {

		AbstractApplicationContext context = new AnnotationConfigApplicationContext(DriverConfig.class);
		Driver driver = (Driver) context.getBean("driverBean");
		System.out.println("Driver class is: "+driver);
		context.close();
		
	}

}
