package org.ontospread.gui.view.nodes.info;
import org.ontospread.gui.view.nodes.PrintNode;

import resources.ApplicationResources;


public class SuperClassNode extends PrintNode{

	public SuperClassNode(String theLabel) {
		super(theLabel);
	}

	@Override
	public String getNodeType() {
		return ApplicationResources.getString("superclass.node");
	}
}
