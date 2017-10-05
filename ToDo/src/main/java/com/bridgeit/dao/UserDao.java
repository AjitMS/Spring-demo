package com.bridgeit.dao;

import com.bridgeit.entity.User;

public interface UserDao {

	public void registerUser(User user);

	public boolean loginUser(User user);
}
