package com.ztchen.service;

import com.ztchen.model.User;

public interface IUserService
{
	public void addUser(User user);
	public void removeUser(String username);
	public User getUser(String username);
}
