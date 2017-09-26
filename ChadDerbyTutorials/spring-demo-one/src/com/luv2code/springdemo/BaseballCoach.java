package com.luv2code.springdemo;

public class BaseballCoach implements Coach {

	// define a private field for dependency

	private FortuneService fortuneService;

	public BaseballCoach(FortuneService fortuneService) {

		this.fortuneService = fortuneService;
	}

	@Override
	public String getDailyWorkout() {
		return "spend 30 minutes in batting practice";
	}

	@Override
	public String getFortune() {

		// use my fortuneService to get a fortune
		return fortuneService.getFortune();

	}
}
