///**
// * @(#) Log4jInit.java Created on 2014-2-24
// *
// * Copyright © 2013 深圳企业云科技有限公司  版权所有
// */
//package com.fcloud.servlet;
//
//import javax.servlet.http.HttpServlet;
//
//import org.apache.log4j.PropertyConfigurator;
//
///**
// * The class <code>Log4jInit</code>
// * 
// * @author Feng OuYang
// * @version 1.0
// */
//public class Log4jInit extends HttpServlet {
//
//	private static final long serialVersionUID = 8733237315512854070L;
//
//	/**
//	 * <li>HttpServlet的初始化方法</li> <li>调用该方法来初始化Log4j日志系统</li>
//	 */
//	public void init() {
//		String prefix = getServletContext().getRealPath("/");
//		String file = getInitParameter("log4j");
//
//		if (file != null) {
//			System.out.println("log4j init success");
//			PropertyConfigurator.configure(prefix + file);
//
//		} else {
//
//			System.out.println("log4j init fail");
//		}
//	}
//}
