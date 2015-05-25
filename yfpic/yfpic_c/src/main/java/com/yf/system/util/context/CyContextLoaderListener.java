package com.yf.system.util.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.cykj.grcloud.remote.client.RemoteServiceScannerConfigurer;
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
			new SysProperties().loadProperties(getClass().getResourceAsStream("/sys.properties"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ServletContext servletContext = event.getServletContext();
		RemoteServiceScannerConfigurer.setBasePackage(servletContext.getInitParameter("ScanServicePackage"));
		RemoteServiceScannerConfigurer.setBaseUrl(SysProperties.getProperty("REMOTE_URL"));
		super.contextInitialized(event);
		
	}
	
	
}
