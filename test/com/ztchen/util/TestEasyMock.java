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
	public void testGetUser()
	{
		//1.创建DAO的Mock对象，目前就进入了record阶段
		IUserDao ud = EasyMock.createMock(IUserDao.class);
		User u = new User(1, "admin", "123");
		//2、记录ud可能会发生的操作的结果
		/*
		 * 表示在dao中调用了getUser方法并且参数为admin的时候，返回值是对象u
		 */
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
