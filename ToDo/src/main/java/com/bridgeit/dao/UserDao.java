package com.bridgeit.dao;

import com.bridgeit.entity.User;

public interface UserDao {

	public void registerUser(User user);

	public boolean loginUser(String email, String password);

	public void validateRegisteredUser(Integer id);
	
	public User getUserByEmail(String email, User user);
	
	public boolean userExists(User user);
	
	public User getUserById(Integer id, User user);
	
	public void resetPassword(String email, String password);
		
}
