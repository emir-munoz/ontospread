package org.ontospread.gui.view.nodes.spread;

import resources.ApplicationResources;

/**
 * Node activated.
 * @author chema
 *
 */
public class ActivateNode extends ConceptTONode{

	public ActivateNode(String theLabel, String uri) {
		super(theLabel,uri);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("activate.node");
	}
}
