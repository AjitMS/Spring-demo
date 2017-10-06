package com.bridgeit.service;

import com.bridgeit.entity.User;

public interface UserService {

	public void registerUser(User user);

	public void loginUser(User user);

	public void sendVerificationLink(String firstName, String email);
}
