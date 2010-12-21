package org.ontospread.gui.view.nodes.info;

import org.ontospread.gui.view.nodes.PrintNode;

import resources.ApplicationResources;


public class InstanceNode extends PrintNode{

	public InstanceNode(String theLabel) {
		super(theLabel);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("instance.node");
	}
}
