/**
 * @(#) SendTask.java Created on 2014-3-12
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.fcloud.bean.Task;
import com.fcloud.dao.TaskMapper;
import com.fcloud.utils.DBHelper;
import com.fcloud.utils.DateTimeUtil;

/**
 * The class <code>SendTask</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class SendTask extends HttpServlet {

	protected Logger logger = Logger.getLogger(SendTask.class);
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
		resp.setContentType("text/html;charset=UTF-8");
		final String content = req.getParameter("content");

		logger.debug(String.format("content:%1$s time:%2$s", content, DateTimeUtil.now()));

		if (null != content) {
			try {
				SqlSessionFactory sf = new DBHelper().getConnection();

				final Task task = new Task();
				task.setContent(content);
				task.setTime(new Date());
				sf.openSession().getMapper(TaskMapper.class).insert(task);

				logger.debug(String.format("insert id:%1$s time:%2$s", task.getId(),
						DateTimeUtil.now()));

				resp.getWriter().write("" + task.getId());
			} catch (IOException e) {
				logger.error("", e);
				resp.getWriter().write("0");
			}
		} else {
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
