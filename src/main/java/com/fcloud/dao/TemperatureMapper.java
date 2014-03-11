/**
 * @(#) TemperatureMapper.java Created on 2014-3-11
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */
package com.fcloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fcloud.bean.Temperature;

/**
 * The class <code>TemperatureMapper</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public interface TemperatureMapper {

	int insert(Temperature temperature);

	List<Temperature> query(@Param(value = "offset") long offset, @Param(value = "limit") long limit);

}
