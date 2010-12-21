package org.ontospread.gui.view.nodes.spread;

import org.ontospread.gui.view.nodes.PrintNode;

public class ConceptTONode extends PrintNode{

	private String conceptUri;
	
	public ConceptTONode(String theLabel) {
		super(theLabel);
		// TODO Auto-generated constructor stub
	}
	public ConceptTONode(String theLabel, String uri) {
		super(theLabel);
		this.conceptUri = uri;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConceptTONode)){
			return false;
		}
		ConceptTONode otherNode = (ConceptTONode) obj;		
		return super.equals(obj) && otherNode.getConceptUri().equals(conceptUri) ;
	}
	public String getConceptUri() {
		return conceptUri;
	}
	public void setConceptUri(String conceptUri) {
		this.conceptUri = conceptUri;
	}


	
}
