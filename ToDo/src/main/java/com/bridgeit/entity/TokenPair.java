package com.bridgeit.entity;

public class TokenPair {
	
	private String userId;
	private String tokenId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
	public String toString() {
		return "tokenPair [userId=" + userId + ", tokenId=" + tokenId + "]";
	}

	public TokenPair(String userId, String tokenId) {
		super();
		this.userId = userId;
		this.tokenId = tokenId;
	}

	public TokenPair() {

	}

}
