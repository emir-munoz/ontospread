package org.ontospread.gui.view.nodes;

import resources.ApplicationResources;
import net.sourceforge.jpowergraph.defaults.DefaultNode;

public class PrintNode extends DefaultNode{

	private String label = "";
	private String type = "";

	public PrintNode(String theLabel){
		this.label = theLabel;
		this.type = ApplicationResources.getString("root.node");
	}

	public PrintNode(String theLabel, String type){
		this.label = theLabel;
		this.type = type;
	}

	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public String getNodeType() {
		return type;
	}


	public boolean equals(Object obj){
		if (!(obj instanceof PrintNode)){
			return false;
		}
		PrintNode otherNode = (PrintNode) obj;
		return (otherNode.getNodeType().equals(getNodeType()) && otherNode.getLabel().equals(label)) ;
	}
}
