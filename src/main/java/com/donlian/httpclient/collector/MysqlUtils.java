package com.donlian.httpclient.collector;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlUtils {
	public static Connection getMysqlConnection() {
		String URL = "jdbc:mysql://10.9.14.136:3306/donlianli?characterEncoding=gbk";
		String user = "root";
		String password = "mysql";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(URL, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
