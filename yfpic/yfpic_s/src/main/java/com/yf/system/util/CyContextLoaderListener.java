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
import com.yf.util.SysProperties;


/**
 * 系统启动时加载
 * @version 1.0
 */
public class CyContextLoaderListener extends ContextLoaderListener implements ServletContextListener {

	protected static final Logger logger = LoggerFactory.getLogger(CyContextLoaderListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) { 
		super.contextDestroyed(event);
	}
	 
	 
	public void contextInitialized(ServletContextEvent event) {
		try {
			new SysProperties().loadProperties(getClass().getResourceAsStream("/yfbase_s.properties"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		super.contextInitialized(event);
		
		ServletContext sc = event.getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		SqlSessionFactory factory = context.getBean(SqlSessionFactory.class);
		new MapperXmlGenerate(factory.getConfiguration(),sc.getInitParameter("contextScanPackage"),"mysql");
		ServletContext servletContext = event.getServletContext();
		if (servletContext != null){
			ObjectFactory.getInstance(servletContext);
		}
		SystemCache.cacheAllDict(); 
	}
}
