package com.yf.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yf.test.util.ExampleSql;

public class GenerateExampleSqllMain {
	protected static final Logger logger = LoggerFactory.getLogger(GenerateExampleSqllMain.class);
	
	public static void main(String[] args) {
		//根据entity.java生成对应的sqlExample文件
		String className = "com.cykj.system.entity.SysDetail";
		String xml = new ExampleSql(className).generate();
		logger.debug(xml);
	}

}
