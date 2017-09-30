package com.sample.profileExample;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.sample.profileExample")
public interface ConfigInterface {
	
	@Autowired
	DataSource source = null;
	
}
