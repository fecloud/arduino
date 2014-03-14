/**
 * @(#) CDATAXppDriver.java Created on 2014-3-14
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.utils;

import java.io.Writer;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * The class <code>CDATAXppDriver</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class CDATAXppDriver extends XppDriver {
	public HierarchicalStreamWriter createWriter(Writer out) {
		return new PrettyPrintWriter(out) {
			boolean cdata = false;

			@Override
			public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
				if (!name.equals("xml")) {
					char[] arr = name.toCharArray();
					if (arr[0] >= 'a' && arr[0] <= 'z') {
						// arr[0] -= 'a' - 'A';
						// ASCII码，大写字母和小写字符之间数值上差32
						arr[0] = (char) ((int) arr[0] - 32);
					}
					name = new String(arr);
				}

				super.startNode(name, clazz);

			}

			@Override
			public void setValue(String text) {
				if (text != null && !"".equals(text)) {
					// 浮点型判断
					Pattern patternInt = Pattern.compile("[0-9]*(\\.?)[0-9]*");
					// 整型判断
					Pattern patternFloat = Pattern.compile("[0-9]+");
					// 如果是整数或浮点数 就不要加[CDATA[]了
					if (patternInt.matcher(text).matches() || patternFloat.matcher(text).matches()) {
						cdata = false;
					} else {
						cdata = true;
					}
				}
				super.setValue(text);
			}

			@Override
			protected void writeText(QuickWriter writer, String text) {
				if (cdata) {
					writer.write("<![CDATA[");
					writer.write(text);
					writer.write("]]>");
				} else {
					writer.write(text);
				}
			}
		};
	}
}
