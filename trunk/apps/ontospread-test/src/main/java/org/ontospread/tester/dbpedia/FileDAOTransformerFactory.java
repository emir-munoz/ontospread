package org.ontospread.tester.dbpedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;


public class FileDAOTransformerFactory extends DAOTransformerFactory {
   
	private static final Logger logger = Logger.getLogger(FileDAOTransformerFactory.class);
	
	private DAOTemplatesFactory groundingTemplates = new DAOTemplatesFactory();
	private TransformerFactory tFactory = TransformerFactory.newInstance();

	public FileDAOTransformerFactory(){
		
	}
    public Transformer getDAOTransformer(String keyTemplates) {
        Transformer transformer = createTransformer(getTemplates(keyTemplates));      
        return transformer;
    }
    public Transformer getDAOTransformer(String keyTemplates, HashMap<String,String>parameters) {
        Transformer transformer = createTransformer(getTemplates(keyTemplates));      
        for (String key : parameters.keySet()) {
			transformer.setParameter(key, parameters.get(key));
		}
        return transformer;
    }
   
    /**
     * Returns the template from a given key
     * 
     * @param keytransform Key of the template
     * @return Template by the key
     */
    private Templates getTemplates(String keytransform) {
        Templates templates = groundingTemplates.get(keytransform);
        if(templates==null) {
            templates = createTemplates(keytransform);
            groundingTemplates.put(keytransform, templates);
        }
        return templates;
    }
    
	/**
     * Create a new template
     * 
     * @param pathTemplates Key of the transformer
     * @return A new transformer object
     */
    private Templates createTemplates(String pathTemplates) {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(pathTemplates);
            StreamSource stream = new StreamSource(inputStream);
            Templates templates = tFactory.newTemplates(stream);
            inputStream.close();
            return templates;
        } catch (TransformerConfigurationException e) {
            throw new DAOTransformerException(e, pathTemplates);
        } catch (FileNotFoundException e) {
        	throw new DAOTransformerException(e, pathTemplates);
		} catch (IOException e) {
			throw new DAOTransformerException(e, pathTemplates);
		}
    }
    
    /**
     * Create a transformer
     * 
     * @param templates Template for the transformer
     * @return The new transformer
     */
    private Transformer createTransformer(Templates templates) {
        try {
            return (templates.newTransformer());
        } catch (TransformerConfigurationException e) {
            throw new DAOTransformerException(e);        
        }        
    }

 
}
