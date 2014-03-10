/**
 * @(#) WXValid.java Created on 2014-2-24
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fcloud.utils.EncoderHandler;
import com.fcloud.utils.Tools;

/**
 * The class <code>WXValid</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class WXValid extends HttpServlet {

	//Logger logger = Logger.getLogger(WXValid.class);

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

		final String signature = req.getParameter("signature");
		final String timestamp = req.getParameter("timestamp");
		final String nonce = req.getParameter("nonce");
		final String echostr = req.getParameter("echostr");

		final String TOKEN = getInitParameter("TOKEN");
		if (null != signature && null != timestamp && null != nonce && null != echostr
				&& null != TOKEN) {
			//logger.debug(String.format(
			//		"signature:%1$s timestamp:%2$s nonce:%3$s echostr:%4$s TOKEN:%5$s", signature,
			//		timestamp, nonce, echostr, TOKEN));

			final String[] list = Tools.toArrayString(TOKEN, timestamp, nonce);
			if (null != list) {
				Arrays.sort(list);
			}

			String weixin = Tools.joinStrings(list);

//			logger.debug("add array:" + weixin);

			weixin = EncoderHandler.encode("SHA1", weixin);
//			logger.debug("array sha1:" + weixin);

			if (signature.equals(weixin)) {
				// 验证成功
				resp.getWriter().write(echostr);
			}else {
				resp.getWriter().write("valid fail");
			}
		} else {
//			logger.warn("require parameter signature,timestamp,nonce,echostr");
		}
	}

}
