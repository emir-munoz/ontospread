package org.ontospread.gui.view.nodes.spread;

import resources.ApplicationResources;

/**
 * Node in activation.
 * @author chema
 *
 */
public class ActivationNode extends ConceptTONode{

	public ActivationNode(String theLabel, String uri) {
		super(theLabel,uri);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("activation.node");
	}
}
