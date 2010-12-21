package org.ontospread.gui.view.nodes.spread;

import resources.ApplicationResources;

/**
 * Node initial.
 * @author chema
 *
 */
public class InitialNode extends ConceptTONode{

	public InitialNode(String theLabel, String uri) {
		super(theLabel,uri);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("initial.node");
	}
}
