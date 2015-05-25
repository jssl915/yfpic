package com.yf.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yf.test.util.MapperXmlEntityAnalysis;

public class GenerateXmlMain {
	protected static final Logger logger = LoggerFactory.getLogger(GenerateXmlMain.class);
	
	public static void main(String[] args) {
		//根据mapper.java生成对应的mapper.xml文件
		String className = "SysRoleMapper";
		String xml = new MapperXmlEntityAnalysis("com.yf.system.mapper."+className,"mysql").mapperXmlGenerate();
		logger.debug(xml);
	}

}
