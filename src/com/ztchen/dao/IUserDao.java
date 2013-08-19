package com.ztchen.dao;

import com.ztchen.model.User;

public interface IUserDao {
	public void addUser(User user);
	public void removeUser(String username);
	public User getUser(String username);
}
