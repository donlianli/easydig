package com.donlian.test.unitils;

import java.util.List;

import javax.sql.DataSource;

public class UserDAOImpl implements UserDAO {
	private DataSource dataSource;
	@Override
	public User findByName(String string, String string2) {
		User u = new User();
		u.setName("jdoe");
		u.setFirstname("jdoe");
		u.setUserName("jdoe");
		return u;
	}

	@Override
	public List<User> findByMinimalAge(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		
	}

}
