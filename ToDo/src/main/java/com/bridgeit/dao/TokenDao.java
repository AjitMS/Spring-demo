package com.bridgeit.dao;

import com.bridgeit.entity.Token;

public interface TokenDao {
 
	public void setTokenIntoDB(Token token, String userId);
}
