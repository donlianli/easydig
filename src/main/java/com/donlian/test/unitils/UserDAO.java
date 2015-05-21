package com.donlian.test.unitils;

import java.util.List;

import javax.sql.DataSource;



public interface UserDAO {

	User findByName(String string, String string2);

	List<User> findByMinimalAge(int i);

	void setDataSource(DataSource dataSource);

}
