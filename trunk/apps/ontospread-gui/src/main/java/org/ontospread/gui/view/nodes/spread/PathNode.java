package org.ontospread.gui.view.nodes.spread;

import resources.ApplicationResources;

public class PathNode extends ConceptTONode {


	public PathNode(String theLabel, String uri) {
		super(theLabel, uri);
	}
	@Override
	public String getNodeType() {
		return ApplicationResources.getString("path.node");
	}

}
