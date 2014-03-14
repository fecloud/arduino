/**
 * @(#) WXValid.java Created on 2014-2-24
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fcloud.bean.weixin.WXMessage;
import com.fcloud.servlet.weixin.dispath.WXProcess;
import com.fcloud.servlet.weixin.dispath.WXTextMessageProccess;
import com.fcloud.utils.EncoderHandler;
import com.fcloud.utils.Tools;
import com.thoughtworks.xstream.XStream;

/**
 * The class <code>WXValid</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class WXPlatform extends HttpServlet {

	Logger logger = Logger.getLogger(WXPlatform.class);

	protected List<WXProcess> wxProcesses = new ArrayList<WXProcess>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {

		wxProcesses.add(new WXTextMessageProccess());
		super.init();
	}

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
			logger.debug(String.format(
					"signature:%1$s timestamp:%2$s nonce:%3$s echostr:%4$s TOKEN:%5$s", signature,
					timestamp, nonce, echostr, TOKEN));

			final String[] list = Tools.toArrayString(TOKEN, timestamp, nonce);
			if (null != list) {
				Arrays.sort(list);
			}

			String weixin = Tools.joinStrings(list);

			logger.debug("add array:" + weixin);

			weixin = EncoderHandler.encode("SHA1", weixin);
			logger.debug("array sha1:" + weixin);

			if (signature.equals(weixin)) {
				// 验证成功
				resp.getWriter().write(echostr);
			} else {
				resp.getWriter().write("valid fail");
			}
		} else {
			logger.warn("require parameter signature,timestamp,nonce,echostr");
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
		// 此处响应平台消息
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		final BufferedReader reader = new BufferedReader(
				new InputStreamReader(req.getInputStream(),"UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line = null;

		while (null != (line = reader.readLine())) {
			buffer.append(line);
		}

		if (buffer.length() > 0) {
			logger.debug("recevie from weixin server:" + buffer.toString());
			// 分析xml
			XStream stream = new XStream();
			stream.alias("xml", WXMessage.class);
			WXMessage message = (WXMessage) stream.fromXML(buffer.toString());
			if (null != message) {
				final String result = messageProcess(message);
				if (null != result) {
					logger.debug("result to weixin server:" + result);
					resp.getWriter().append(result);
				}
			}
		}

	}

	protected String messageProcess(WXMessage message) {

		for (WXProcess p : wxProcesses) {
			if (p.getMessageType().equals(message.getMsgType())) {
				return p.process(message);
			}
		}
		return null;
	}
}
