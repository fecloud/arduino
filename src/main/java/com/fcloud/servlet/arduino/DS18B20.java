/**
 * @(#) DS18B20.java Created on 2014-2-28
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet.arduino;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.fcloud.bean.Temperature;
import com.fcloud.dao.TemperatureMapper;
import com.fcloud.utils.DBHelper;
import com.fcloud.utils.DateTimeUtil;

/**
 * The class <code>DS18B20</code>
 * 
 * @author braver
 * @version 1.0
 */
public class DS18B20 extends HttpServlet {

	protected Logger logger = Logger.getLogger(DS18B20.class);

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
		logger.debug(String.format("temperature:%1$s time:%2$s", temperature, DateTimeUtil.now()));

		SqlSessionFactory sf = new DBHelper().getConnection();
		Temperature bean = new Temperature();
		try {
			bean.setTemperature(Double.valueOf(temperature));
			bean.setTime(new Date());
			int id = sf.openSession().getMapper(TemperatureMapper.class).insert(bean);

			logger.debug(String.format("insert id:%1$s time:%2$s", id, DateTimeUtil.now()));

			resp.getWriter().write(id);
		} catch (Exception e) {
			logger.error("", e);
			resp.getWriter().write("0");
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
