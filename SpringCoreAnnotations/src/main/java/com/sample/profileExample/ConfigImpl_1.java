package com.sample.profileExample;

import org.springframework.context.annotation.Profile;

@Profile("firstConfig")
public class ConfigImpl_1 implements ConfigInterface {

	public ConfigImpl_1() {
		
	}
}
