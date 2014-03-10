/**
 * @(#) DBHelper.java Created on 2014-3-4
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class <code>DBHelper</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public final class DBHelper {

	public class LocalMysql {

		public String getJDBC() {
			String jdbc = "jdbc:mysql://%1$s:%2$s/%3$s?user=%4$s&password=%5$s&characterEncoding=UTF-8";
			return String.format(jdbc, "localhost", "3306", "arduino", "root", "root");
		}

	}

	public class JDMysql {

		public String getJDBC() {
			String jdbc = "jdbc:mysql://%1$s:%2$s/%3$s?user=%4$s&password=%5$s&characterEncoding=UTF-8";
			return String.format(jdbc, "rds6v2uvvrennaa.mysql.rds.aliyuncs.com",
					3306, "db7k81jhgfm13wla",
					"db7k81jhgfm13wla", "ouyangfeng");
		}
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public  Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection(new JDMysql().getJDBC());
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
