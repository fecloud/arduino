/**
 * @(#) GetDS18B20.java Created on 2014-2-28
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fcloud.utils.DBHelper;
import com.fcloud.utils.DateTimeUtil;

/**
 * The class <code>GetDS18B20</code>
 * 
 * @author braver
 * @version 1.0
 */
public class GetDS18B20 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(GetDS18B20.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		logger.debug("GetDS18B20 time:" + DateTimeUtil.now());

		Connection conn = new DBHelper().getConnection();
		try {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM temperature order by time desc limit 0,20");
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.first()) {
				Timestamp timestamp = resultSet.getTimestamp(resultSet.findColumn("time"));
				double temperature = resultSet.getDouble(resultSet.findColumn("temperature"));
				printLine(timestamp, temperature, resp.getWriter());
				while (resultSet.next()) {
					timestamp = resultSet.getTimestamp(resultSet.findColumn("time"));
					temperature = resultSet.getDouble(resultSet.findColumn("temperature"));
					printLine(timestamp, temperature, resp.getWriter());
				}
				resultSet.close();
				stmt.close();
				conn.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			resp.getWriter()
					.write(String
							.format("<li ><a href=\"#\" class=\"ui-btn ui-btn-icon-right ui-icon-carat-r\"  style=\"color:red\">%1$s %2$s</a></li>",
									"0.0", "error"));
		}

	}

	private void printLine(Timestamp timestamp, double temperature, PrintWriter writer) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
		writer.write(String
				.format("<li ><a href=\"#\" class=\"ui-btn ui-btn-icon-right ui-icon-carat-r\" >%1$s %2$s</a></li>",
						temperature, time));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
