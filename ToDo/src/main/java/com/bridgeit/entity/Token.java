package com.bridgeit.entity;

import org.springframework.stereotype.Component;

@Component("token")
public class Token {

	private Integer userId;
	private String tokenType;
	private String tokenValue;

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public String getTokenType() {
		return tokenType;
	}

	public Token(Integer userId, String tokenType, String tokenValue) {
		super();
		this.userId = userId;
		this.tokenType = tokenType;
		this.tokenValue = tokenValue;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "Token [userId=" + userId + ", tokenType=" + tokenType + ", tokenValue=" + tokenValue + "]";
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
