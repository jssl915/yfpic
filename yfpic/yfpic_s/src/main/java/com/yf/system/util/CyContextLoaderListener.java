package com.yf.system.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cykj.grcloud.mybatis.MapperXmlGenerate;
import com.cykj.grcloud.mybatis.pagination.PaginationInterceptor;
import com.yf.util.SysProperties;
/**
 * 系统启动时加载
 */
public class CyContextLoaderListener extends ContextLoaderListener implements ServletContextListener {
	protected static final Logger logger = LoggerFactory.getLogger(CyContextLoaderListener.class);
	public void contextInitialized(ServletContextEvent event){
		super.contextInitialized(event);
		ServletContext sc = event.getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		SqlSessionFactory factory = context.getBean(SqlSessionFactory.class);
		new SysProperties().loadProperties(getClass().getResourceAsStream("/service.properties"));
		PaginationInterceptor.database=SysProperties.getProperty("database");
		new MapperXmlGenerate(factory.getConfiguration(),SysProperties.getProperty("contextScanPackage"),PaginationInterceptor.database);
		//加载数据字典缓存
		SystemCache.cacheAllDict(); 
	}
}
