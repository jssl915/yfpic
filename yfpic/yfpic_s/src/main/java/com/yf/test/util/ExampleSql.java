package com.yf.test.util;

import java.lang.reflect.Field;

import org.springframework.util.ClassUtils;

public class ExampleSql {
	private Class<?> clazz;
	private StringBuilder sb = new StringBuilder();
	
	public ExampleSql(String entityName){
		try {
			clazz = ClassUtils.forName(entityName,ClassUtils.getDefaultClassLoader());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LinkageError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String generate(){
		sb.append("\r\n\t<sql id=\"Example_Where_Clause\">\r\n");
		sb.append("\t\t<where>\r\n");
		Field[] fields=clazz.getDeclaredFields();
		for(Field f:fields){
			String columnEntity = f.getName(); //字段名
			String columnTable = f.getName().replaceAll("[A-Z]", "_$0").toUpperCase(); //列名
			if ("serialVersionUID".equals(columnEntity)) {
				continue;
			}
			sb.append("\t\t\t<if test=\"@com.cykj.grcloud.util.Ognl@isNotEmpty( "+columnEntity+" )\">\r\n");
	    	sb.append("\t\t\t\t AND t." +columnTable+ " = #{" +f.getName()+ "}\r\n");
	    	sb.append("\t\t\t</if>\r\n");
		}
		sb.append("\t\t</where>\r\n");
		sb.append("\t</sql>\r\n");
		return sb.toString();
	}
}
