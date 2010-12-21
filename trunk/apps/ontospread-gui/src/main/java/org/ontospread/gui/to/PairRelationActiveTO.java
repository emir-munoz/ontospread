package org.ontospread.gui.to;

import org.ontospread.to.ScoredConceptTO;

public class PairRelationActiveTO {

	private ScoredConceptTO[] activeConceptsTOs;
	private ScoredConceptTO[] relationConceptsTOs;
	public ScoredConceptTO[] getActiveConceptsTOs() {
		return activeConceptsTOs;
	}
	public void setActiveConceptsTOs(ScoredConceptTO[] activeConceptsTOs) {
		this.activeConceptsTOs = activeConceptsTOs;
	}
	public ScoredConceptTO[] getRelationConceptsTOs() {
		return relationConceptsTOs;
	}
	public void setRelationConceptsTOs(ScoredConceptTO[] relationConceptsTOs) {
		this.relationConceptsTOs = relationConceptsTOs;
	}
	public PairRelationActiveTO(ScoredConceptTO[] activeConceptsTOs, ScoredConceptTO[] relationConceptsTOs) {
		super();
		this.activeConceptsTOs = activeConceptsTOs;
		this.relationConceptsTOs = relationConceptsTOs;
	}
	public PairRelationActiveTO() {
		super();
	}
	
	
}
