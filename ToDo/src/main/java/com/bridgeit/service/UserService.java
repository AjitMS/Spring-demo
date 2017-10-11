package com.bridgeit.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;

public interface UserService {

	public void registerUser(User user);

	public boolean loginUser(String email, String password);

	public boolean userExists(User user);

	public User getUserByEmail(String email, User user);

	public void validateRegisteredUser(String id);

	public void sendRegistrationVerificationLink(String firstName, String email);

	public void sendLoginVerificationToken(User user, Token token, HttpServletRequest request);

	public void resetPassword(User user, HttpServletRequest request);

}
