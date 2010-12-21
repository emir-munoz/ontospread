package org.ontospread.gui.controller;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextLocator {
	    private static final String BEANS_XML = "resources/ontospread-beans.xml";
	    
	    private static final Logger logger = Logger.getLogger(ApplicationContextLocator.class);
	    
	    private static ApplicationContext applicationContext;

	    /**
	     * Singleton (for efficiency and consistency)
	     * 
	     * @return
	     */
	    public synchronized static ApplicationContext getApplicationContext() {
	        if ( applicationContext == null ) {
	            logger.info("Creating application context");
	            applicationContext = new ClassPathXmlApplicationContext(BEANS_XML);
	        }
	        return applicationContext;
	    }
	    
	    public static void setApplicationContext(ApplicationContext applicationContext) {
	        ApplicationContextLocator.applicationContext = applicationContext;
	    }
}
