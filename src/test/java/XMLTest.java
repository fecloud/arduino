import com.fcloud.bean.weixin.WXMessage;
import com.fcloud.utils.CDATAXppDriver;
import com.thoughtworks.xstream.XStream;

/**
 * @(#) XMLTest.java Created on 2014-3-14
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */

/**
 * The class <code>XMLTest</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class XMLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xml = "<xml> <ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName> <CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId></xml>";
		XStream stream = new XStream(new CDATAXppDriver());
		stream.alias("xml", WXMessage.class);
		WXMessage message = (WXMessage) stream.fromXML(xml);
		System.out.println(message);
		message.setContent("112\nff\nggg");
		
		System.err.println(stream.toXML(message));
	}
}
