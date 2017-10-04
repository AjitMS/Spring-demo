package com.bridgeit.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.entity.User;

@Service("userService")
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void registerUser(User user) {

		// hibernate code here

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		// open session

		// store user
		// close session
		return;

	}

	@Transactional
	public void loginUser(User user) {
		// hibernate code here
		// open session
		// store user
		// close session
		return;

	}

}
