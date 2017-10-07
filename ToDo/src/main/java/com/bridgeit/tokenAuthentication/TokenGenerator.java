package com.bridgeit.tokenAuthentication;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

public class TokenGenerator {

	@Autowired
	Token token = new Token();

	public Token generateToken() {

		UUID uuid = UUID.randomUUID();
		String randomUUID = uuid.toString().replaceAll("-", "");
		System.out.println("Random UUID is: "+randomUUID);
		token.setTokenId(randomUUID);
		return token;
	}
}
 