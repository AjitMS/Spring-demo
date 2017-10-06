package com.bridgeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.UserDao;
import com.bridgeit.emailUtility.EmailVerification;
import com.bridgeit.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;
	@Autowired
	EmailVerification verifyEmail;

	@Transactional
	public void registerUser(User user) {

		dao.registerUser(user);
		System.out.println("Register Success in service");
	}

	@Transactional
	public void loginUser(User user) {
		dao.loginUser(user);
		System.out.println("Login Success");

	}

	@Override
	public void sendVerificationLink(String id, String email) {
		String link = "http://localhost:8080/ToDo/" + id + "/homepage";

		verifyEmail.sendMail("bridgeit@gmail.com", email, "Successful Registration", link);

	}

}
