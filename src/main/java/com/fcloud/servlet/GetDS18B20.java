/**
 * @(#) GetDS18B20.java Created on 2014-2-28
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.fcloud.bean.Temperature;
import com.fcloud.bean.web.WebBean;
import com.fcloud.dao.TemperatureMapper;
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
		
		resp.setContentType("application/json;charset=UTF-8");
		logger.debug("GetDS18B20 time:" + DateTimeUtil.now());

		SqlSessionFactory sf = new DBHelper().getConnection();

		List<Temperature> list = sf.openSession().getMapper(TemperatureMapper.class).query(0, 30);

		if (null != list && list.size() > 0) {
			WebBean<List<Temperature>> bean = new WebBean<List<Temperature>>(list, 200, null);
			resp.getWriter().write(bean.toJOSON());

		} else {
//			resp.getWriter()
//					.write(String
//							.format("<li ><a href=\"#\" class=\"ui-btn ui-btn-icon-right ui-icon-carat-r\"  style=\"color:red\">%1$s %2$s</a></li>",
//									"0.0", "error"));
			WebBean<String> bean = new WebBean<String>("error", 500, "error");
			resp.getWriter().write(bean.toJOSON());
		}

	}

//	private void printLine(Date timestamp, double temperature, PrintWriter writer) {
//		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
//		writer.write(String
//				.format("<li ><a href=\"#\" class=\"ui-btn ui-btn-icon-right ui-icon-carat-r\" >%1$s %2$s</a></li>",
//						temperature, time));
//		
//	}

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
