package com.fcloud.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * 串行化/反串行化 POJO对象
 * 
 * @author 郭天良
 */

public class JSON {

	static Logger logger = Logger.getLogger(JSON.class);

	/**
	 * json解析错误
	 */
	public static final String JSON_ParseError = "JSON_ParseError";

	private static ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getInstance() {
		return mapper;
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T fromJsonAsArray(String jArrayString, TypeReference<?> valueTypeRef) {
		try {
			return (T) mapper.readValue(jArrayString, valueTypeRef);
		} catch (IOException ex) {
			logger.error("", ex);
		}

		return null;
	}

	public static <T> T fromJsonAsObject(String jObjectString, Class<T> pojoClass) {
		try {
			return (T) mapper.readValue(jObjectString, pojoClass);
		} catch (IOException ex) {

			logger.error("", ex);
		}

		return null;
	}

	public static String toJson(Object pojo) {
		if (pojo != null) {
			try {
				return mapper.writeValueAsString(pojo);
			} catch (IOException ex) {
				logger.error("", ex);
			}

		}
		return null;
	}
}