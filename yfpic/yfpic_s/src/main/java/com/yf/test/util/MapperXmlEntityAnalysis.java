package com.yf.test.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.cykj.grcloud.mybatis.annotation.Column;
import com.cykj.grcloud.mybatis.annotation.Table;
import com.cykj.grcloud.util.Ognl;

/**
 * 根据实体类注解解析实体类
 */
public class MapperXmlEntityAnalysis {
	protected static final Logger logger = LoggerFactory.getLogger(MapperXmlEntityAnalysis.class);
	private String database = ""; //mysql或者oracle
	private String tableName = "";//表名
	private String entityName = ""; //实体类名称
	private String mapperName = ""; //mapper.java名称
	private String seqName[] = null;//默认是没有序列
	private String autoIncrement = "";//mysql自增字段，默认没有
	private List<String> pkIds = new ArrayList<String>();  //主键
	private List<String> columns = new ArrayList<String>(); //需要写入sql的列
	private List<String> columnTables =new ArrayList<String>();//需要写入sql的列对应数据库中的字段
	private StringBuilder sb = new StringBuilder(); //存mapper.xml
	
	//构造函数，执行此构造函数会得到database,entityName,pkIds,columns,columnTables
	public MapperXmlEntityAnalysis(String className,String database){
		Class<?> entity = null;
		try {
			Class<?> clazz = ClassUtils.forName(className,ClassUtils.getDefaultClassLoader());//.asSubclass(GenericMapper.class);
			//获取mapper对应的实体CLASS
			entity = (Class<?>) (((ParameterizedType) clazz.getGenericInterfaces()[0]).getActualTypeArguments()[0]);
			this.mapperName = clazz.getName();
			this.entityName = entity.getName();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		this.database = database;
		//如果没有Table注解，则使用默认
		String baseName = entity.getSimpleName();
		this.tableName = "T" + baseName.replaceAll("[A-Z]", "_$0").toUpperCase();//表名默认格式为T_SYS_USER,根据实体类得到
		
		//如果没有Table这个注解的话，全部使用默主规则
		if (!entity.isAnnotationPresent(Table.class)) {
			//noAnnotationTableInit(entity);
			logger.error("实体类"+entityName+"没有@Table注解,该注解不能省略...");
		}else{
			//如果有Table注解
			hasAnnotationTableInit(entity);
		}
	}
	
	/**
	 * 当实体类上面有Table注解
	 * 1、没有主键
	 * 2、oracle没有序列,mysql没有自增
	 * 3、如果没有tabName,表名对应规则:实体类SysUser-->表名T_SYS_USER
	 * 4、如果存在字段没有columnName,数据库列对应规则:字段userName-->列USER_NAME
	 */
	public void hasAnnotationTableInit(Class<?> entity){
		Table antable = (Table) entity.getAnnotation(Table.class);
		//如果注解里定义了tabName,则使用注解里的tableName
		if(Ognl.isNotEmpty(antable.tabName())){
			tableName = antable.tabName();
		}
		//如果使用的是oracle数据库，并且定义了序列,则使用注解中的序列
		if (database.equals("oracle")&&Ognl.isNotEmpty(antable.seqName())) {
			seqName = antable.seqName().split(",");
		}
		//如果使用的是mysql数据库，并且定义了自增字段，则该字段自增
		if (database.equals("mysql")&&Ognl.isNotEmpty(antable.autoIncrement())) {
			autoIncrement = antable.autoIncrement();
		}
		//如果该字段为主键，则添加进主键list
		if (Ognl.isNotEmpty(antable.pkId())) {
			String pkeyIds = antable.pkId();
			String pkId[] = pkeyIds.split(",");
			for(String pId:pkId){
				pkIds.add(pId);
			}
		}
		
		//遍历该实体类，查找注解column,如果定义了column，则使用注解中的column,否则使用自定义规则userName-->USER_NAME
		Field[] fields=entity.getDeclaredFields();
		for(Field f:fields){
			//过滤掉serialVersionUID
			if ("serialVersionUID".equals(f.getName())) {
				continue;
			}
			String columnEntity = f.getName(); //字段名
			String columnTable = f.getName().replaceAll("[A-Z]", "_$0").toUpperCase(); //列名
			Column column=(Column)f.getAnnotation(Column.class);
			//如果该字段有有column
			if(Ognl.isNotEmpty(column)){
				if(Ognl.isNotEmpty(column.columnName())){
					columnTable = column.columnName();
				}
				columns.add(columnEntity);
				columnTables.add(columnTable);
			}
		}
		
	}
	/**
	 * mapper.xml生成方法
	 */
	public String mapperXmlGenerate(){
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
		sb.append(" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\r\n");
		sb.append("<mapper namespace=\"").append(mapperName).append("\">\r\n");
		resultMap();
		whereClauseMap();
		persistMap();
		//keywords
		if(Ognl.isNotEmpty(pkIds)){
			updateByIdMap();
			mergeByIdMap();
			removeByIdMap();
			findByIdMap();
		}
		countByConditionMap();
		findEntitysByConditionMap();
		removeByConditionMap();
		sb.append("</mapper>");
		return sb.toString();
	}
	
	/**
	 * resultMap生成方法
	 */
	public void resultMap(){
		sb.append("\t<resultMap id=\"BaseResultMap\" type=\""+entityName+"\" >\r\n");
		for(int i=0;i<columns.size();i++){
			String columnName = columns.get(i);
			String property = columnTables.get(i);
			if(pkIds.contains(columnName)){
				sb.append("\t\t<id column=\""+property+ "\" property=\""+columnName+ "\"/>\r\n");
			}else{
				sb.append("\t\t<result column=\""+property+ "\" property=\""+columnName+ "\"/>\r\n");
			}
		}
		sb.append("\t</resultMap>\r\n");
	}
	public void whereClauseMap(){
		sb.append("\t<sql id=\"Example_Where_Clause\">\r\n");
		sb.append("\t<where>\r\n");
		for(int i=0;i<columns.size();i++){
			String columnName = columns.get(i);
			String property = columnTables.get(i);
    	    sb.append("\t\t<if test=\"@com.cykj.grcloud.util.Ognl@isNotEmpty( "+columnName+" )\">\r\n");
    	    sb.append("\t\t\t AND " +property+ " = #{" +columnName+ "}\r\n");
    	    sb.append("\t\t</if>\r\n");
	     }
		sb.append("\t</where>\r\n");
		sb.append("\t</sql>\r\n");
	}
	/**
	 * persist生成方法
	 */
	public void persistMap(){
		sb.append("\t<insert id=\"insert\" parameterType=\""+ entityName+ "\">\r\n");	
		//对于oracle数据库，如果序列seqName不为空
		if(database.equals("oracle")&&Ognl.isNotEmpty(seqName)){
			sb.append("\t<selectKey keyProperty=\""+seqName[0]+"\" order=\"BEFORE\" statementType=\"PREPARED\" resultType=\"java.lang.Long\"\r\n");
			sb.append("\t\tSELECT \""+ seqName[1]+ "\".NEXTVAL FROM DUAL\r\n");
			sb.append("\t</selectKey>\r\n");
		}
		sb.append("\t insert into ").append(tableName).append("(");
		String s1 ="",s2="";
		for(int i =0;i<columnTables.size()-1;i++){
			//对于mysql数据库,如果自增字段不为空
			if(database.equals("mysql")&&Ognl.isNotEmpty(autoIncrement)){
				if(columns.get(i).equals(autoIncrement)){
					continue;
				}
			}
			s1 = s1+columnTables.get(i)+",";
			s2 = s2+"#{"+columns.get(i)+"},";
		}
		s1 = s1+columnTables.get(columnTables.size()-1);
		s2 = s2+"#{"+columns.get(columns.size()-1)+"}";
		sb.append(s1+") \r\n\t values("+s2+") \r\n\t</insert>\r\n\n");
	}
	
	
	/**
	 * updateById生成方法
	 */
	public void updateByIdMap(){
		sb.append("\t<update id=\"updateById\" parameterType=\""+ entityName+ "\">\r\n");	
		sb.append("\t\t update ").append(tableName).append("\r\n");;
		sb.append("\t\t<trim prefix=\"SET\" suffixOverrides=\",\" > ").append("\r\n");
		List<String> pks =new ArrayList<String>();
		for(int i =0;i<columnTables.size();i++){
			 if(pkIds.contains(columns.get(i))){
				 pks.add(columnTables.get(i)+"=#{"+columns.get(i)+"}");
			 }else{
		    	 sb.append("\t\t\t" +columnTables.get(i)+ " = #{" +columns.get(i)+ "}, \r\n");
			 }
		}
		sb.append("\t\t</trim>\r\n");
		sb.append("\t\t where ");
		for(int i = 0;i<pks.size()-1;i++){
			sb.append(pks.get(i)+" and ");
		}
		sb.append(pks.get(pks.size()-1)+"\r\n");
		sb.append("\r\n\t</update>\r\n\n");
	}
	

	/**
	 * mergeById生成方法
	 */
	public void mergeByIdMap(){
		sb.append("\t<update id=\"mergeById\" parameterType=\""+ entityName+ "\">\r\n");
		sb.append("\t\t update ").append(tableName).append("\r\n");;
		sb.append("\t\t<trim prefix=\"SET\" suffixOverrides=\",\" > ").append("\r\n");
		List<String> pks =new ArrayList<String>();
		for(int i =0;i<columnTables.size();i++){
			 if(pkIds.contains(columns.get(i))){
				 pks.add(columnTables.get(i)+"=#{"+columns.get(i)+"}");
			 }else{
				 sb.append("\t\t\t<if test=\"@com.cykj.grcloud.util.Ognl@isNotEmpty( "+columns.get(i)+" )\">\r\n");
		    	 sb.append("\t\t\t\t" +columnTables.get(i)+ " = #{" +columns.get(i)+ "}, \r\n");
		    	 sb.append("\t\t\t</if>\r\n");
			 }
		}
		sb.append("\t\t</trim>\r\n");
		sb.append("\t\t where ");
		for(int i = 0;i<pks.size()-1;i++){
			sb.append(pks.get(i)+" and ");
		}
		sb.append(pks.get(pks.size()-1)+"\r\n");
		sb.append("\r\n\t</update>\r\n\n");
	}
	
	/**
	 * removeById生成方法
	 */
	public void removeByIdMap(){
    	sb.append("\t<delete id=\"removeById\">\r\n");	
		sb.append("\t delete from ").append(tableName).append(" ");
		sb.append(" where ");	
		List<String> pks =new ArrayList<String>();
		for(int i =0;i<columnTables.size()-1;i++){
			 if(pkIds.contains(columns.get(i))){
				 pks.add(columnTables.get(i)+"=#{"+columns.get(i)+"}");
			 }
		}
		for(int i = 0;i<pks.size()-1;i++){
			sb.append(pks.get(i)+" and ");
		}
		sb.append(pks.get(pks.size()-1)+"\r\n");
		sb.append("\t</delete>\r\n");
	}
	
	/**
	 * findById生成方法
	 */
	public void findByIdMap(){
		sb.append("\t<select id=\"findById\" resultMap=\"BaseResultMap\">\r\n");	
		sb.append("\t select").append(" ");
		List<String> pks =new ArrayList<String>();
		for(int i =0;i<columnTables.size()-1;i++){
			 if(pkIds.contains(columns.get(i))){
				 pks.add(columnTables.get(i)+"=#{"+columns.get(i)+"}");
			 }
			sb.append(columnTables.get(i)+", ");
		}
		sb.append(columnTables.get(columnTables.size()-1));
		
		sb.append(" from ").append(tableName+" t").append(" ");
		sb.append(" where ");	
		for(int i = 0;i<pks.size()-1;i++){
			sb.append(pks.get(i)+" and ");
		}
		sb.append(pks.get(pks.size()-1)+"\r\n");
		sb.append("\t</select>\r\n");
	}		

	/**
	 * countByCondition生成方法
	 */
	public void countByConditionMap(){
		sb.append("\t<select id=\"countByCondition\" parameterType=\"java.util.Map\" resultType=\"java.lang.Integer\">\r\n");	
		sb.append("\t\t select count(1) from ").append(tableName+" t").append("\r\n");		
		sb.append("\t\t<include refid=\"Example_Where_Clause\"/>\r\n");
		sb.append("\t</select>\r\n");
	}	


	/**
	 * findEntitysByConditionMap生成方法
	 */
	public void findEntitysByConditionMap(){
		sb.append("\t<select id=\"findEntitysByCondition\" parameterType=\"java.util.Map\" resultMap=\"BaseResultMap\">\r\n");	
		sb.append("\t select").append(" ");
		for(int i =0;i<columnTables.size()-1;i++){
			sb.append(columnTables.get(i)+", ");
		}
		sb.append(columnTables.get(columnTables.size()-1));
		sb.append(" from ").append(tableName+" t").append("\r\n");
		sb.append("\t\t<include refid=\"Example_Where_Clause\"/>\r\n");
		sb.append("\t\t<if test=\"@com.cykj.grcloud.util.Ognl@isNotEmpty(orderByClause)\">\r\n");
		sb.append("\t\t\t order by ${orderByClause}\r\n");
		sb.append("\t\t </if>\r\n");		
		sb.append("\t</select>\r\n");
	}	
	
	/**
	 * removeByCondition生成方法
	 */
	public void removeByConditionMap(){
		sb.append("\t<delete id=\"removeByCondition\" parameterType=\"java.util.Map\">\r\n");	
		sb.append("\t\t delete from ").append(tableName).append("\r\n");		
		sb.append("\t\t<include refid=\"Example_Where_Clause\"/>\r\n");
		sb.append("\t</delete>\r\n");
	}
	
}
