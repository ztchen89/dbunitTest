package com.ztchen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dbutil {
	private static final String DBURL="jdbc:mysql://localhost:3306/test";
	private static final String DBUSER="root";
	private static final String DBPASSWORD="1";
	
	public static Connection getConnection() throws SQLException
	{
		Connection conn = null;
		conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		
		return conn;
	}
	
	public static void close(Connection conn)
	{
		try 
		{
			if(null != conn) 
				conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt)
	{
		try 
		{
			if(null != pstmt) 
				pstmt.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs)
	{
		try 
		{
			if(null != rs) 
				rs.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
