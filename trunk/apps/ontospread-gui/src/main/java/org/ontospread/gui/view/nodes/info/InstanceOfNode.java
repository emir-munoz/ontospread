package org.ontospread.gui.view.nodes.info;

import org.ontospread.gui.view.nodes.PrintNode;

import resources.ApplicationResources;


public class InstanceOfNode extends PrintNode{

	public InstanceOfNode(String theLabel) {
		super(theLabel);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("instanceOf.node");
	}
}
