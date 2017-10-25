package com.bridgeit.entity;

import org.springframework.stereotype.Component;

@Component("token")
public class Token {

	private Integer userId;
	private String tokenType;


	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "Token [userId=" + userId + ", tokenType=" + tokenType + "]";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Token() {

	}

	public Token(Integer userId) {
		super();
		this.userId = userId;
	}

}
