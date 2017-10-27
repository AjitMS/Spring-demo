package com.bridgeit.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;

public interface UserService {

	public void registerUser(User user);

	public boolean loginUser(String email, String password);

	public boolean userExists(User user);

	public User getUserByEmail(String email, User user);

	public User getUserById(Integer id, User user);
	
	public void validateRegisteredUser(Integer id);

	public void sendRegistrationVerificationLink(Integer id, String email);

	public void sendLoginVerificationToken(User user, Token accessToken, HttpServletRequest request);

	public void sendResetPasswordMail(User user, HttpServletRequest request, Token token);

	public void resetPassword(String email, String password);

}
