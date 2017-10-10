package com.bridgeit.service;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;

public interface UserService {

	public void registerUser(User user);

	public boolean loginUser(User user);

	public boolean userExists(User user);

	public User getUserByEmail(String email, User user);

	public void validateRegisteredUser(String id);

	public void sendRegistrationVerificationLink(String firstName, String email);

	public void sendLoginVerificationToken(User user, Token token);

}
