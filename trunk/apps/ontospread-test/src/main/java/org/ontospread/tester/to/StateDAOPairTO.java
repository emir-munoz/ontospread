package org.ontospread.tester.to;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.state.OntoSpreadState;


public class StateDAOPairTO {

	private OntologyDAO ontoDAO;
	private OntoSpreadState ontoSpreadState;
	public OntologyDAO getOntoDAO() {
		return ontoDAO;
	}
	public void setOntoDAO(OntologyDAO ontoDAO) {
		this.ontoDAO = ontoDAO;
	}
	public OntoSpreadState getOntoSpreadState() {
		return ontoSpreadState;
	}
	public void setOntoSpreadState(OntoSpreadState ontoSpreadState) {
		this.ontoSpreadState = ontoSpreadState;
	}
	public StateDAOPairTO(OntologyDAO ontoDAO, OntoSpreadState ontoSpreadState) {
		super();
		this.ontoDAO = ontoDAO;
		this.ontoSpreadState = ontoSpreadState;
	}
	
}
