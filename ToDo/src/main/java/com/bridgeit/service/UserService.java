package com.bridgeit.service;

import com.bridgeit.entity.User;
import com.bridgeit.tokenAuthentication.Token;

public interface UserService {

	public void registerUser(User user);

	public boolean loginUser(User user);

	public void sendVerificationLink(String firstName, String email);
	
	public void validateRegisteredUser(String id);
	
	public void verifyLoggedInUser(User user, Token token);
	
	public User getUserByEmail(String email, User user);
}
