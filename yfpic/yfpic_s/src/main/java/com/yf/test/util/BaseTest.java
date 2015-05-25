package com.yf.test.util;
 
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cykj.grcloud.mybatis.MapperXmlGenerate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
public class BaseTest extends AbstractJUnit4SpringContextTests {

	@Before
	public void before()  {//这里可以预加载	
		SqlSessionFactory factory = applicationContext.getBean(SqlSessionFactory.class); 
		new MapperXmlGenerate(factory.getConfiguration(),"com.yf.**.mapper","mysql");
	}
}
