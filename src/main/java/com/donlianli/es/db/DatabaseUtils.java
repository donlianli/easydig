package com.donlianli.es.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtils {
	public static Connection getOracleConnection() {
		String URL = "jdbc:oracle:thin:@192.168.1.105:1521:orcl";
		String user = "code";
		String password = "code";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(URL, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
