package com.ztchen.service;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.ztchen.dao.IUserDao;
import com.ztchen.model.User;
import com.ztchen.model.UserException;
import com.ztchen.util.EntitiesHelper;

public class TestUserServiceByEasyMock
{
	private IUserDao ud;
	private IUserService us;
	private User baseUser;
	
	@Before
	public void setUp()
	{
		ud = EasyMock.createStrictMock(IUserDao.class);
		us = new UserService(ud);
		baseUser = new User(1, "admin", "123");
	}
	
	@Test
	public void testGetUser()
	{
		EasyMock.expect(ud.getUser("admin")).andReturn(baseUser);
		EasyMock.replay(ud);
		User tu = us.getUser("admin");
		EntitiesHelper.assertUser(tu, baseUser);
		EasyMock.verify(ud);
	}
	
	@Test
	public void testAddNotExistUser()
	{
		EasyMock.expect(ud.getUser(baseUser.getUsername())).andReturn(null);
		ud.addUser(baseUser);
		EasyMock.expectLastCall();
		EasyMock.replay(ud);
		us.addUser(baseUser);
		EasyMock.verify(ud);
	}
	
	@Test(expected=UserException.class)
	public void testAddExistUser()
	{
		EasyMock.expect(ud.getUser(baseUser.getUsername())).andReturn(baseUser);
		ud.addUser(baseUser);
		EasyMock.expectLastCall();
		EasyMock.replay(ud);
		us.addUser(baseUser);
		EasyMock.verify(ud);
	}
	
	@Test
	public void testRemoveUser()
	{
		ud.removeUser("admin");
		EasyMock.expectLastCall();
		EasyMock.replay(ud);
		us.removeUser("admin");
		EasyMock.verify(ud);
	}
	
}
