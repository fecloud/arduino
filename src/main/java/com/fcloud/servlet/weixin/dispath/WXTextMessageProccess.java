/**
 * @(#) TextMessageProccess.java Created on 2014-3-14
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet.weixin.dispath;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.fcloud.bean.Temperature;
import com.fcloud.bean.weixin.WXMessage;
import com.fcloud.dao.TemperatureMapper;
import com.fcloud.utils.DBHelper;
import com.fcloud.utils.DateTimeUtil;
import com.fcloud.utils.Tools;
import com.thoughtworks.xstream.XStream;

/**
 * The class <code>TextMessageProccess</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class WXTextMessageProccess implements WXProcess {

	Logger logger = Logger.getLogger(WXTextMessageProccess.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fcloud.servlet.weixin.dispath.WXProcess#getMessageType()
	 */
	@Override
	public String getMessageType() {
		return WXMessageType.TEXT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fcloud.servlet.weixin.dispath.WXProcess#process(com.fcloud.bean.weixin
	 * .WXMessage)
	 */
	@Override
	public String process(WXMessage message) {

		String result = null;
		if (null != message.getContent()) {
			final String content = message.getContent();
			if (content.contains("wd") || content.contains("温度") || Tools.isNum(content)) {
				result = temperature(content);
			} else {
				result = "发送 wd 、温度 等关键字 既可查询传感器温度信息/:love";
			}
		}
		WXMessage wxResult = new WXMessage();
		wxResult.setToUserName(message.getFromUserName());
		wxResult.setFromUserName(message.getToUserName());
		wxResult.setCreateTime(System.currentTimeMillis());
		wxResult.setMsgType(WXMessageType.TEXT);
		wxResult.setContent(result);

		XStream stream = new XStream();
		stream.alias("xml", WXMessage.class);
		return stream.toXML(wxResult);
	}

	private String temperature(String content) {
		logger.debug("temperature");
		try {
			StringBuilder builder = new StringBuilder();
			SqlSessionFactory sf = new DBHelper().getConnection();
			List<Temperature> list = null;
			if (Tools.isNum(content)) {
				builder.append("以下是您查询的温度信息:\n");
				list = sf.openSession().getMapper(TemperatureMapper.class)
						.query(0, Long.valueOf(content));
				if (list != null) {
					for (Temperature t : list) {
						builder.append(t.getTemperature() + "℃  "
								+ DateTimeUtil.toText(t.getTime(), "HH:mm") +"\n");
					}

				}
			} else {
				list = sf.openSession().getMapper(TemperatureMapper.class).query(0, 1);

				if (list != null && list.size() > 0) {
					Temperature t = list.get(0);
					builder.append("当前温度" + t.getTemperature() + "℃");
					if (DateTimeUtil.isSun(t.getTime())) {
						builder.append("/:sun");
					} else {
						builder.append("/:moon");
					}
				}
			}
			return builder.toString();

		} catch (IOException e) {
			logger.error("temperature", e);
		}
		return "么么哒,服务出错啦/::Q";

	}
}
