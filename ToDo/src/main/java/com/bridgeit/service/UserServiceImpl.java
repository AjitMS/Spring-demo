package com.bridgeit.service;

import com.bridgeit.dao.UserDao;
import com.bridgeit.dao.UserDaoImpl;
import com.bridgeit.entity.User;

public class UserServiceImpl implements UserService {

	UserDao dao;

	public void registerUser(User user) {

		dao = new UserDaoImpl();
		dao.registerUser(user);
		System.out.println("Success");
	}

	public void loginUser(User user) {
		dao = new UserDaoImpl();
		dao.loginUser(user);
		System.out.println("Success");

	}

}
