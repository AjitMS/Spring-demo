package com.bridgeit.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.UserDao;
import com.bridgeit.emailUtility.EmailVerification;
import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;

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
	@Override
	@Transactional
	public Integer registerUser(User user) {
		System.out.println("Register Success in service");
		return dao.registerUser(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgeit.service.UserService#loginUser(com.bridgeit.entity.User)
	 * logging user by authenticating email, password at service level
	 */
	@Transactional
	public boolean loginUser(String email, String password) {
		if (dao.loginUser(email, password)) {
			System.out.println("Login Success");
			return true;
		}
		return false;

	}

	@Override
	@Transactional
	public User getUserByEmail(String email, User user) {
		user = dao.getUserByEmail(email, user);
		return user;
	}

	@Override
	@Transactional
	public boolean userExists(User user) {
		if (dao.userExists(user))
			return true;
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
	public void validateRegisteredUser(Integer id) {
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
	public void sendRegistrationVerificationLink(Integer id, String email) {

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
	public void sendLoginVerificationToken(User user, Token accessToken, HttpServletRequest request) {
		String subject = "Bridgelabz Secure Login Link";
		String link = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
				+ request.getServletPath() + user.getId() + "/" + accessToken.getTokenValue();
		System.out.println("link is: " + link);
		String msg = "Dear " + user.getFirstName().toUpperCase() + ", Login from below secure link\n" + link + "";
		verifyEmail.sendMail("bridgeit@gmail.com", user.getEmail(), subject, msg);

	}

	@Override
	public void sendResetPasswordMail(User user, HttpServletRequest request, Token token) {

		String subject = "Bridgelabz Secure Login Link";
		String link = request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
				+ "/login/resetpasswordtoken/" + user.getId() + "/" + token.getTokenValue();
		String msg = "Dear " + user.getFirstName() + ", Access below link to reset password\n" + link + "";
		verifyEmail.sendMail("bridgeit@gmail.com", user.getEmail(), subject, msg);

	}

	@Override
	@Transactional
	public void resetPassword(String email, String password) {
		dao.resetPassword(email, password);
		return;
	}

	@Override
	public User getUserById(Integer id, User user) {
		user = dao.getUserById(id, user);
		return user;
	}

}
