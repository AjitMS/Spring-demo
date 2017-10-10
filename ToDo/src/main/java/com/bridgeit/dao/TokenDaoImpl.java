package com.bridgeit.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.entity.Token;

public class TokenDaoImpl implements TokenDao {

	@Autowired
	SessionFactory factory;

	@Override
	public void setTokenIntoDB(Token token, String userId) {
		Session session = factory.openSession();

	}

}
