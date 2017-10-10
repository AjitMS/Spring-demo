package com.bridgeit.entity;

import org.springframework.stereotype.Component;

@Component("token")
public class Token {

	private String tokenId;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Token(String tokenId) {
		this.tokenId = tokenId;
	}

	public Token() {
		tokenId = null;
	}

	@Override
	public String toString() {
		return "Token [tokenId=" + tokenId + "]";
	}

}
