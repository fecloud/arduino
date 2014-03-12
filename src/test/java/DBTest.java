import java.io.IOException;
import java.util.Date;

import com.fcloud.bean.Task;
import com.fcloud.bean.Temperature;
import com.fcloud.dao.TaskMapper;
import com.fcloud.dao.TemperatureMapper;
import com.fcloud.utils.DBHelper;

//import com.fcloud.utils.DBHelper;

/**
 * @(#) DBTest.java Created on 2014-3-4
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */

/**
 * The class <code>DBTest</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
public class DBTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		Task task = new Task();
		task.setTime(new Date());
		task.setContent("content");
		System.err.println(new DBHelper().getConnection().openSession().getMapper(TaskMapper.class).insert(task ));
		
		Temperature temperature = new Temperature();
		temperature.setTemperature(12.5);
		temperature.setTime(new Date());
		System.err.println(new DBHelper().getConnection().openSession().getMapper(TemperatureMapper.class).insert(temperature));
		
	}

}
