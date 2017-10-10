package com.bridgeit.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component("token")
@Entity
public class Token {
	
	@Id
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
