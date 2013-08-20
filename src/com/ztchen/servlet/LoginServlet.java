package com.ztchen.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ztchen.model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet 
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/json;charset=utf-8");
		resp.getWriter().print("abc");
		req.getRequestDispatcher("/test.jsp").forward(req, resp);
	}
	
	public boolean isLogin(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		if(null == session)
		{
			return false;
		}
		
		User u = (User) session.getAttribute("loginUser");
		if(null == u)
		{
			return false;
		}
		
		return true;
	}
}
