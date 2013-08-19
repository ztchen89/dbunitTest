package com.ztchen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ztchen.model.User;
import com.ztchen.util.Dbutil;

public class UserDao implements IUserDao
{

	public void addUser(User user)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = Dbutil.getConnection();
			String sql = "insert into t_user(username,password) values(?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			
			pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			Dbutil.close(pstmt);
			Dbutil.close(conn);
		}
	}

	public void removeUser(String username)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = Dbutil.getConnection();
			String sql = "delete from t_user where username=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.executeUpdate();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			Dbutil.close(pstmt);
			Dbutil.close(conn);
		}
	}

	public User getUser(String username)
	{
		User user = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = Dbutil.getConnection();
			String sql = "select * from t_user where username=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			Dbutil.close(rs);
			Dbutil.close(pstmt);
			Dbutil.close(conn);
		}
		
		return user;
	}

}
