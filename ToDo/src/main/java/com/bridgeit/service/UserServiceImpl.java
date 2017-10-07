package com.bridgeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.UserDao;
import com.bridgeit.emailUtility.EmailVerification;
import com.bridgeit.entity.User;
import com.bridgeit.tokenAuthentication.Token;

/**
 * @author Ajit Shikalgar
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;
	@Autowired
	EmailVerification verifyEmail;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgeit.service.UserService#registerUser(com.bridgeit.entity.User)
	 * registering user at service level
	 */
	@Transactional
	public void registerUser(User user) {

		dao.registerUser(user);
		System.out.println("Register Success in service");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgeit.service.UserService#loginUser(com.bridgeit.entity.User)
	 * logging user by authenticating email, password at service level
	 */
	@Transactional
	public boolean loginUser(User user) {
		if (dao.loginUser(user)) {
			System.out.println("Login Success");
			return true;
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.service.UserService#validateRegisteredUser(java.lang.String)
	 * service level method to validate registered user. changing isValid to '1'
	 * from '0'
	 */
	@Transactional
	public void validateRegisteredUser(String id) {
		dao.validateRegisteredUser(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgeit.service.UserService#sendVerificationLink(java.lang.String,
	 * java.lang.String) method to send verification mail to registering user.
	 * mandatory step to verify mail of user attempting to register
	 */
	@Override
	public void sendVerificationLink(String id, String email) {

		String link = "http://localhost:8080/ToDo/register/verifyuser/" + id;
		verifyEmail.sendMail("bridgeit@gmail.com", email, "Confirm Registration", link);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgeit.service.UserService#verifyLoggedInUser(com.bridgeit.entity.User,
	 * java.lang.String) method to implement token based authentication by
	 * associating a id of login attempting user AND its corresponding generated
	 * token this method sends a mail to the user mail and after accessing that
	 * mail, user is redirected to home page after both tokens match.
	 */
	@Override
	public void verifyLoggedInUser(User user, Token token) {
		String subject = "Bridgelabz Secure Login Link";
		String link = "localhost:8080/ToDo/login/" + user.getId() + "/" + token.getTokenId();
		String msg = "Dear " + user.getFirstName() + ", Login from below secure link\n" + link + "";
		verifyEmail.sendMail("bridgeit@gmail.com", user.getEmail(), subject, msg);

	}

	@Override
	public User getUserByEmail(String email, User user) {
		user = dao.getUserByEmail(email, user);
		return user;
	}

}
