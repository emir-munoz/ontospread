package org.ontospread.gui.utils;

import org.ontospread.dao.JenaOntologyDAOImpl;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.gui.demo.*;
import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.loader.JenaRDFModelWrapper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.model.resources.ExternalizeFilesResourceLoader;
import org.ontospread.model.resources.ResourceLoader;

public class DAOUtils {
	public static OntoSpreadModelWrapper createOntoModelWrapper(String []filenames){
		ResourceLoader resource = new ExternalizeFilesResourceLoader(filenames);
		JenaOWLModelWrapper model = new JenaOWLModelWrapper(resource);
		return model;
	}
	
	public static OntologyDAO createOntologyDAO(String []filenames) {
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}
	
	public static OntologyDAO createDBPediaOntologyDAO(String []filenames) {
		OntologyDAO ontoDAO = new WebOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}
	
	public static OntoSpreadModelWrapper createModelWrapper(String []filenames) {
		ResourceLoader resource = new ExternalizeFilesResourceLoader(filenames);
		return new JenaRDFModelWrapper(resource);	
		
	}
}
