package org.ontospread.gui.view.nodes.info;

import org.ontospread.gui.view.nodes.PrintNode;

import resources.ApplicationResources;


public class ConceptNode extends PrintNode{

	public ConceptNode(String theLabel) {
		super(theLabel);
	}

	@Override
	public String getNodeType() {
		return ApplicationResources.getString("concept.node");
	}

}
