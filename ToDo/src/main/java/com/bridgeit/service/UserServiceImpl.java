package com.bridgeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.UserDao;
import com.bridgeit.dao.UserDaoImpl;
import com.bridgeit.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao dao;

	@Transactional
	public void registerUser(User user) {

		dao = new UserDaoImpl();
		dao.registerUser(user);
		System.out.println("Register Success");
	}

	@Transactional
	public void loginUser(User user) {
		dao = new UserDaoImpl();
		dao.loginUser(user);
		System.out.println("Login Success");

	}

}
