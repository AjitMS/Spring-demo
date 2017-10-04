package com.bridgeit.dao;

import com.bridgeit.entity.User;

public interface UserDao {

	public void registerUser(User user);

	public void loginUser(User user);
}
