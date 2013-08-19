package com.ztchen.service;

import com.ztchen.dao.IUserDao;
import com.ztchen.dao.UserDao;
import com.ztchen.model.User;
import com.ztchen.model.UserException;

public class UserService implements IUserService
{
	private IUserDao userDao;
	
	public UserService()
	{
		userDao = new UserDao();
	}
	
	public UserService(IUserDao userDao)
	{
		super();
		this.userDao = userDao;
	}
	
	public void addUser(User user)
	{
		if(null != getUser(user.getUsername()))
			throw new UserException("用户名已经存在");
		userDao.addUser(user);
	}

	public void removeUser(String username)
	{
		userDao.removeUser(username);
	}

	public User getUser(String username)
	{
		return userDao.getUser(username);
	}

}
