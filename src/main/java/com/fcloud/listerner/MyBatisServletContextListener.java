/**
 * @(#) MyBatisServletContextListener.java Created on 2014-3-11
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.listerner;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 * The class <code>MyBatisServletContextListener</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class MyBatisServletContextListener implements ServletContextListener {

	Logger logger = Logger.getLogger(MyBatisServletContextListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("1123");
		SqlSessionFactory sessionFactory = null;
		String resource = "mybatis-config.xml";
		try {
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources
					.getResourceAsReader("/" + resource));
			ServletContext ctx = arg0.getServletContext();
			ctx.setAttribute("sqlSessionFactory", sessionFactory);

		} catch (IOException e) {
			logger.error("contextInitialized", e);
			e.printStackTrace();
		}

	}

}
