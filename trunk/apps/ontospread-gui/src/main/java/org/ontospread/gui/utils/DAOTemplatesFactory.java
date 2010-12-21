package org.ontospread.gui.utils;

import java.util.HashMap;

import javax.xml.transform.Templates;

import org.apache.log4j.Logger;

/**
 * @author chema
 *
 * Cache for the templates
 * 
 */
public class DAOTemplatesFactory {
    
    private static Logger logger= Logger.getLogger(DAOTemplatesFactory.class);
	private HashMap<String,Templates> templates;
    
	public DAOTemplatesFactory() {	    
	    templates = new HashMap<String,Templates>();
	}
    
	public void put(String key, Templates templates) {	    
	    this.templates.put(key, templates);
	}
    
	public Templates get(String key) {
	    return (this.templates.get(key));
	}
	
}
