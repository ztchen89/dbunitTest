package com.ztchen.util;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.ztchen.dao.IUserDao;
import com.ztchen.dao.UserDao;
import com.ztchen.model.User;

public class TestDbutil
{
	@Test
	public void testGetUser()
	{
		try
		{
			//先备份数据
			testBackupOneTable();
			//创建dbunit的Connnection，需要传入一个jdbc的connection作为参数
			IDatabaseConnection conn = new DatabaseConnection(Dbutil.getConnection());
			/*
			 * FlatXmlDataSet用于获取基于属性存储的属性值
			 * XmlDataSet用于获取基于节点类型存储的属性值
			 */
			IDataSet ds = new FlatXmlDataSet(
					new FlatXmlProducer(
							new InputSource(
									TestDbutil.class.getClassLoader().getResourceAsStream("t_user.xml"))));
			
			//删除数据库中的原有数据，并插入测试数据
			DatabaseOperation.CLEAN_INSERT.execute(conn, ds);

			//从DAO中获取数据并测试
			IUserDao userDao = new UserDao();
			User tu = userDao.getUser("admin");
			
			assertEquals(tu.getId(), 1);
			assertEquals(tu.getUsername(), "admin");
			assertEquals(tu.getPassword(), "123");
		} catch (DatabaseUnitException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//还原数据
		testRecovery();
	}
	
	@Test
	public void testBackupAllTable()
	{
		//备份所有表
		try
		{	
			IDatabaseConnection conn = new DatabaseConnection(Dbutil.getConnection());
			//根据conn创建相应的dataset，这个dataset包含了所有的表
			IDataSet ds = conn.createDataSet();
			//将ds中的数据通过FlatXmlDataSet的格式写到文件中，这个就是备份
			FlatXmlDataSet.write(ds, new FileWriter("e:/test.xml"));
			
		} catch (DatabaseUnitException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBackupOneTable()
	{
		//备份指定的一张表
		try
		{
			IDatabaseConnection conn = new DatabaseConnection(Dbutil.getConnection());
			//通过QueryDataSet可以选择要处理的表作为数据集
			QueryDataSet qds = new QueryDataSet(conn);
			//添加t_user这张表作为备份表
			qds.addTable("t_user");
			FlatXmlDataSet.write(qds, new FileWriter("e:/t_user.xml"));
			
		} catch (DatabaseUnitException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRecovery()
	{
		try
		{
			IDatabaseConnection conn = new DatabaseConnection(Dbutil.getConnection());
			//根据备份文件创建dataset
			IDataSet ds = new FlatXmlDataSet(
					new FlatXmlProducer(
							new InputSource(
									new FileInputStream("e:/t_user.xml"))));
			//删除测试数据，插入备份数据
			DatabaseOperation.CLEAN_INSERT.execute(conn, ds);
			
		} catch (DatabaseUnitException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
