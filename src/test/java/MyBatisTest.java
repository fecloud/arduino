import java.io.IOException;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.fcloud.bean.Temperature;
import com.fcloud.dao.TemperatureMapper;

/**
 * @(#) MyBatisTest.java Created on 2014-3-11
 *
 * Copyright © 2013 深圳企业云科技有限公司  版权所有
 */

/**
 * The class <code>MyBatisTest</code>
 * 
 * @author Feng OuYang
 * @version 1.0
 */
/**
 * myBatis数据库连接测试
 * 
 * @author db2admin
 * 
 */
public class MyBatisTest {
	/**
	 * 获得MyBatis SqlSessionFactory
	 * SqlSessionFactory负责创建SqlSession，一旦创建成功，就可以用SqlSession实例来执行映射语句
	 * ，commit，rollback，close等方法。
	 * 
	 * @return
	 */
	private static SqlSessionFactory getSessionFactory() {
		SqlSessionFactory sessionFactory = null;
		String resource = "mybatis-config.xml";
		try {
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources
					.getResourceAsReader(resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}

	public static void main(String[] args) {
		SqlSession sqlSession = getSessionFactory().openSession();
		TemperatureMapper temperatureMapper = sqlSession.getMapper(TemperatureMapper.class);
		for(Temperature t:temperatureMapper.query(0, 10)){
			System.out.println(t.getTime());
			t.setTime(new Date());
			temperatureMapper.insert(t);
		}

		
	}

}