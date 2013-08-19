package com.ztchen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

public class AbstractDbutilTestCase
{
	public static IDatabaseConnection dbunitConn;
	private File tempFile;//存放备份数据
	
	@BeforeClass
	public static void init() throws DatabaseUnitException, SQLException
	{
		dbunitConn = new DatabaseConnection(Dbutil.getConnection());
	}
	
	protected IDataSet createDataSet(String tname) throws DataSetException
	{
		InputStream is = AbstractDbutilTestCase
				.class.getClassLoader()
				.getResourceAsStream("dbunit_xml/" + tname + ".xml");
		Assert.assertNotNull("dbunit的基本数据文件不存在",is);
		
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
		
	}

	private void writeBackupFile(IDataSet ds) throws IOException, DataSetException
	{
		tempFile = File.createTempFile("backData", "xml");
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}
	
	//备份所有的表
	protected void backupAllTable() throws DataSetException, IOException, SQLException
	{
		IDataSet ds = dbunitConn.createDataSet();
		writeBackupFile(ds);
	}
	
	//备份特定的某些表
	protected void backupCustomTable(String[] tname) throws DataSetException, IOException
	{
		QueryDataSet ds = new QueryDataSet(dbunitConn);
		for (String str : tname)
		{
			ds.addTable(str);
		}
		writeBackupFile(ds);
	}
	
	//备份特定的一张表
	protected void backupOneTable(String tname) throws DataSetException, IOException
	{
		backupCustomTable(new String[]{tname});
	}
	
	//从当初备份的xml文件中还原数据
	protected void recoveryTable() throws FileNotFoundException, DatabaseUnitException, SQLException
	{
		IDataSet ds = new FlatXmlDataSet(
				new FlatXmlProducer(
						new InputSource(
								new FileInputStream(tempFile))));
		DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ds);
	}
	
	@AfterClass
	public static void destory()
	{
		try
		{
			if(null != dbunitConn) 
				dbunitConn.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}

