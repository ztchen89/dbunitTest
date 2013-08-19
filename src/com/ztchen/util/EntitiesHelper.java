package com.ztchen.util;


import org.junit.Assert;

import com.ztchen.model.User;

public class EntitiesHelper
{
	private static User baseUser = new User(1, "admin", "123");
	
	public static void assertUser(User expected, User actual)
	{
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getPassword(), actual.getPassword());
	}
	
	public static void assertUser(User expected)
	{
		assertUser(expected, baseUser);
	}
}
