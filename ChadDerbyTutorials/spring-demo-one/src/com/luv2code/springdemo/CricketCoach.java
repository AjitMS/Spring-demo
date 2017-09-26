package com.luv2code.springdemo;

public class CricketCoach implements Coach {

	private FortuneService fortuneService;

	@Override
	public String getDailyWorkout() {

		return "Do Fast Bowling for 15 minutes daily";
	}

	public void setFortuneService(FortuneService fortuneService) {
		this.fortuneService = fortuneService;
	}


	public CricketCoach() {

	}
	
	@Override
	public String getFortune() {
		return fortuneService.getFortune();
	}

}
