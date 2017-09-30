package com.sample.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class DriverConfig {

	@Bean(name = "driverBean")
	@Description("It returns Driver Bean which is injected by license bean")
	public Driver driver() {
		return new Driver();
	}
	
	
	@Bean(name = "license")
	@Description("It returns License Bean to be injected in Driver bean")
	public License license() {
		return new License();
	}

}
