package com.yf.system.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.yf.util.SysProperties;
/**
 * 系统启动时加载
 */
public class CyContextLoaderListener extends ContextLoaderListener implements ServletContextListener {
	protected static final Logger logger = LoggerFactory.getLogger(CyContextLoaderListener.class);
	public void contextInitialized(ServletContextEvent event){
		new SysProperties().loadProperties(getClass().getResourceAsStream("/sys.properties"));
		super.contextInitialized(event);
	}
}
