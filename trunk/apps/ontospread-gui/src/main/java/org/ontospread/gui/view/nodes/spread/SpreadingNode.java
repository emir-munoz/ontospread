package org.ontospread.gui.view.nodes.spread;

import resources.ApplicationResources;

/**
 * Spreaded concept.
 * @author chema
 *
 */
public class SpreadingNode extends ConceptTONode{

	public SpreadingNode(String theLabel, String uri) {
		super(theLabel,uri);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("spreading.node");
	}
}
