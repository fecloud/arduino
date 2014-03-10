import com.fcloud.utils.DBHelper;

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
	 */
	public static void main(String[] args) {

		new DBHelper().getConnection();
		
	}

}
