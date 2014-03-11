/**
 * @(#) WebTemperature.java Created on 2014-3-11
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.bean.web;

import com.fcloud.utils.JSON;

/**
 * The class <code>WebTemperature</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class WebBean<T> {

	private T data;

	private int status;

	private String error;

	public String toJOSON() {
		return JSON.toJson(this);
	}

	/**
	 * @param data
	 * @param status
	 * @param error
	 */
	public WebBean(T data, int status, String error) {
		super();
		this.data = data;
		this.status = status;
		this.error = error;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
