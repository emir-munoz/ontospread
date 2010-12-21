package org.ontospread.gui.view.nodes.info;
import org.ontospread.gui.view.nodes.PrintNode;

import resources.ApplicationResources;


public class SubClassNode extends PrintNode{

	public SubClassNode(String theLabel) {
		super(theLabel);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("subclass.node");
	}
}
