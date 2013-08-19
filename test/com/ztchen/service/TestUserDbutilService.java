package com.ztchen.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ztchen.dao.UserDao;
import com.ztchen.model.User;
import com.ztchen.model.UserException;
import com.ztchen.util.AbstractDbutilTestCase;
import com.ztchen.util.EntitiesHelper;

public class TestUserDbutilService extends AbstractDbutilTestCase
{
	private IUserService us;
	private IDataSet ds;
	
	@Before
	public void setUp() throws DataSetException, IOException
	{
		us = new UserService(new UserDao());
		backupOneTable("t_user");
		ds = createDataSet("t_user");
	}
	
	@Test
	public void testGetUser() throws DatabaseUnitException, SQLException
	{
		DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ds);
		User tu = us.getUser("admin");
		EntitiesHelper.assertUser(tu);
	}
	
	@Test
	public void testAddUserNotExits() throws Exception
	{
		/*
		 * DatabaseOperation.TRUNCATE_TABLE
		 * 是把数据库表里的数据全部清除，然后插入的数据id也从零开始
		*/
		DatabaseOperation.TRUNCATE_TABLE.execute(dbunitConn, ds);
		User u = new User(1, "admin", "123");
		us.addUser(u);
		User tu = us.getUser("admin");
		EntitiesHelper.assertUser(tu, u);
	}
	
	@Test(expected=UserException.class)
	public void testAddUserExits() throws Exception
	{
		DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ds);
		User u = new User(1, "admin", "123");
		us.addUser(u);
	}

	@Test
	public void testRemoveUser() throws DatabaseUnitException, SQLException
	{
		DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ds);
		us.removeUser("admin");
		User tu = us.getUser("admin");
		Assert.assertNull(tu);
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException
	{
		recoveryTable();
	}
}
