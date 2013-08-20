package com.ztchen.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ztchen.model.User;

public class TestLoginServletByEasyMock
{
	private LoginServlet servlet;
	private HttpServletRequest req;
	private HttpSession session;
	
	@Before
	public void setUp()
	{
		servlet = new LoginServlet();
		req = EasyMock.createStrictMock(HttpServletRequest.class);
		session = EasyMock.createStrictMock(HttpSession.class);
	}
	
	@Test
	public void testSessionIsNull()
	{
		EasyMock.expect(req.getSession()).andReturn(null);
		EasyMock.replay(req,session);
		Assert.assertFalse(servlet.isLogin(req));
		EasyMock.verify(req,session);
	}
	
	@Test
	public void testSessionNoUser()
	{
		EasyMock.expect(req.getSession()).andReturn(session);
		EasyMock.expect(session.getAttribute("loginUser")).andReturn(null);
		EasyMock.replay(req,session);
		Assert.assertFalse(servlet.isLogin(req));
		EasyMock.verify(req,session);
	}
	
	@Test
	public void testSessionHasUser()
	{
		EasyMock.expect(req.getSession()).andReturn(session);
		EasyMock.expect(session.getAttribute("loginUser")).andReturn(new User());
		EasyMock.replay(req,session);
		Assert.assertTrue(servlet.isLogin(req));
		EasyMock.verify(req,session);
	}
}
