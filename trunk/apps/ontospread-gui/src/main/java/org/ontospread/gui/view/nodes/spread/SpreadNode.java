package org.ontospread.gui.view.nodes.spread;

import resources.ApplicationResources;

/**
 * Spreaded concept.
 * @author chema
 *
 */
public class SpreadNode extends ConceptTONode{

	public SpreadNode(String theLabel, String uri) {
		super(theLabel,uri);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("spread.node");
	}
}
