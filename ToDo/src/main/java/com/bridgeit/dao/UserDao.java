package com.bridgeit.dao;

import com.bridgeit.entity.User;

public interface UserDao {

	public void registerUser(User user);

	public boolean loginUser(String email, String password);

	public void validateRegisteredUser(String id);
	
	public User getUserByEmail(String email, User user);
	
	public boolean userExists(User user);
		
}
