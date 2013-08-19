package com.ztchen.util;

import org.easymock.EasyMock;
import org.junit.Test;

import com.ztchen.dao.IUserDao;
import com.ztchen.model.User;
import com.ztchen.service.IUserService;
import com.ztchen.service.UserService;

public class TestEasyMock
{
	@Test
	public void testGetUserMock()
	{
		//1.创建DAO的Mock对象，目前就进入了record阶段
		//createMock方式不需要验证顺序，顺序打乱不会发生异常
		IUserDao ud = EasyMock.createMock(IUserDao.class);
		User u = new User(1, "admin", "123");
		//2、记录ud可能会发生的操作的结果
		/*
		 * 表示在dao中调用了getUser方法并且参数为admin的时候，返回值是对象u
		 */
		//必须把UserService和UserDao交互的所有过程都记录下来
		EasyMock.expect(ud.getUser("admin")).andReturn(u);
		ud.removeUser("tt");
		//以下用来操作没有返回值的方法
		EasyMock.expectLastCall();

		//3、进入测试阶段，也就是replay阶段
		EasyMock.replay(ud);
		//创建service和dao的关联
		IUserService us = new UserService(ud);
		//完成测试
		User tu = us.getUser("admin");
		EntitiesHelper.assertUser(tu, u);
		//4、验证交互关系是否正确
		EasyMock.verify(ud);
	}
	
	@Test
	public void testGetUserStrictMock()
	{
		//1.创建DAO的Mock对象，目前就进入了record阶段
		//StrictMock方式调用要验证顺序
		IUserDao ud = EasyMock.createStrictMock(IUserDao.class);
		User u = new User(1, "admin", "123");
		//2、记录ud可能会发生的操作的结果
		/*
		 * 表示在dao中调用了getUser方法并且参数为admin的时候，返回值是对象u
		 */
		//必须把UserService和UserDao交互的所有过程都记录下来
		ud.removeUser("tt");
		//以下用来操作没有返回值的方法
		EasyMock.expectLastCall();
		EasyMock.expect(ud.getUser("admin")).andReturn(u);
		//3、进入测试阶段，也就是replay阶段
		EasyMock.replay(ud);
		//创建service和dao的关联
		IUserService us = new UserService(ud);
		//完成测试
		User tu = us.getUser("admin");
		EntitiesHelper.assertUser(tu, u);
		//4、验证交互关系是否正确
		EasyMock.verify(ud);
	}
	
	
	
	
}
