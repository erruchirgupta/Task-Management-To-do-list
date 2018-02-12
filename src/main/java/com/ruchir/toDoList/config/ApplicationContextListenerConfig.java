package com.ruchir.toDoList.config;

import java.util.concurrent.ExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * 
 * @author	Ruchir Gupta
 * @email	erruchirgupta@gmail.com
 * @date	11:44:25 PM
 * 
 **/

@Configuration
public class ApplicationContextListenerConfig implements ServletContextListener {

	private static Logger logger = Logger.getLogger(ApplicationContextListenerConfig.class.getName());

	@Autowired
	private ExecutorService executorService;

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		logger.info("Begining contextDestroyed()");
		executorService.shutdown();
		logger.info("End contextDestroyed()");
		
	}	

}