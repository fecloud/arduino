/**
 * @(#) DS18B20.java Created on 2014-2-28
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet.arduino;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fcloud.utils.DBHelper;

/**
 * The class <code>DS18B20</code>
 * 
 * @author braver
 * @version 1.0
 */
public class DS18B20 extends HttpServlet {

	// protected Logger logger = Logger.getLogger(DS18B20.class);

	public static final String Temperature = "temperature";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		final String temperature = req.getParameter("temperature");
		// logger.debug(String.format("temperature:%1$s time:%2$s", temperature,
		// DateTimeUtil.now()));
		try {
			if (null != temperature) {
				Connection conn = new DBHelper().getConnection();
				String sql = "INSERT INTO temperature (temperature,time) values(?,?)";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setDouble(1, Double.valueOf(temperature));
				Date date = new Date();//获得系统时间.
		       String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				
				stmt.setTimestamp(2, Timestamp.valueOf(nowTime));
				stmt.execute();
				stmt.close();
				conn.close();
				resp.getWriter().append("sucess").flush();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resp.getWriter().append("error").flush();
		}
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
