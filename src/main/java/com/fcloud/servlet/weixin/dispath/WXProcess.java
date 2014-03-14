/**
 * @(#) WXProcess.java Created on 2014-3-14
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.servlet.weixin.dispath;

import com.fcloud.bean.weixin.WXMessage;

/**
 * The class <code>WXProcess</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public interface WXProcess {

	public String process(WXMessage message);
	
	String getMessageType();
	
}
