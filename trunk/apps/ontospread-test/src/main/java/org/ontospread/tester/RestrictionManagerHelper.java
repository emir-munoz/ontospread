package org.ontospread.tester;

import org.ontospread.process.run.OntoSpreadDegradationFunction;
import org.ontospread.process.run.OntoSpreadDegradationFunctionImpl;
import org.ontospread.process.run.OntoSpreadDegradationFunctionIterationsImpl;
import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionContext;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinActivationValue;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionNotMaxTime;
import org.ontospread.tester.xmlbind.ActivationRestriction;
import org.ontospread.tester.xmlbind.ContextRestriction;
import org.ontospread.tester.xmlbind.FunctionType;
import org.ontospread.tester.xmlbind.MaxConceptsRestriction;
import org.ontospread.tester.xmlbind.MinConceptsRestriction;
import org.ontospread.tester.xmlbind.RestrictionType;
import org.ontospread.tester.xmlbind.TimeRestriction;

public class RestrictionManagerHelper {


	OntoSpreadRestrictionNotMaxTime spreadTime;
	OntoSpreadRestrictionMinActivationValue spreadMinAct;
	OntoSpreadRestrictionMinConcepts spreadMinConcepts;
	OntoSpreadRestrictionMaxConcepts spreadMaxConcepts;
	OntoSpreadRestrictionContext spreadContext;
	OntoSpreadDegradationFunction function;
	
	TimeRestriction timeRestriction;
	ContextRestriction contextRestriction;
	MinConceptsRestriction minConceptsRestriction;
	MaxConceptsRestriction maxConceptsRestriction;
	ActivationRestriction activation;
	FunctionType functionType;
	
	public RestrictionManagerHelper(){
		
	}
	
	
	public  OntoSpreadRestriction buildTimeRestriction(boolean iterate) {
		if(iterate){
			this.spreadTime.setMaxTime((this.spreadTime.getMaxTime()+this.timeRestriction.getConfig().getStep()));
		}
		return this.spreadTime;
		
	}
	public  OntoSpreadRestriction buildContextRestriction(boolean iterate) {
		if(iterate){
			this.spreadContext.setCurrentRetries(0);
			this.spreadContext.setMaxRetries(this.spreadContext.getMaxRetries()+this.contextRestriction.getRetries().getStep());
		}
		return this.spreadContext;
	}
	public  OntoSpreadRestriction buildActivationRestriction(boolean iterate) {
		if(iterate){
			this.spreadMinAct.setMinActivationValue(this.spreadMinAct.getMinActivationValue()+this.activation.getConfig().getStep());
		}
		return this.spreadMinAct;
	}
	public  OntoSpreadRestriction buildMinRestriction(boolean iterate) {
		if(iterate){
			this.spreadMinConcepts.setMinConceptsSpreaded(this.spreadMinConcepts.getMinConceptsSpreaded()+this.minConceptsRestriction.getConfig().getStep());
		}
		return this.spreadMinConcepts;
	}
	public  OntoSpreadRestriction  buildMaxRestriction(boolean iterate) {
		if(iterate){
			this.spreadMaxConcepts.setMaxConceptsSpreaded(this.spreadMaxConcepts.getMaxConceptsSpreaded()+this.maxConceptsRestriction.getConfig().getStep());
		}
		return  this.spreadMaxConcepts;
	}
	
	public boolean hasStopTime(){
		return (Float.compare(this.spreadTime.getMaxTime(), this.timeRestriction.getConfig().getStop())>0);
	}

	
	public boolean hasStopAct(){		
		return (Double.compare(this.spreadMinAct.getMinActivationValue(), this.activation.getConfig().getStop())>0);
	}
	
	public boolean hasStopMinConcepts(){
		return (this.spreadMinConcepts.getMinConceptsSpreaded() > this.minConceptsRestriction.getConfig().getStop());
	}

	public boolean hasStopMaxConcepts(){
		return (this.spreadMaxConcepts.getMaxConceptsSpreaded() > this.maxConceptsRestriction.getConfig().getStop());
	}

	
	public boolean hasStopContextRetries(){
		return (this.spreadContext.getMaxRetries() > this.contextRestriction.getRetries().getStop());
	}
	
   public void restart(RestrictionType restriction){
		if(restriction instanceof MaxConceptsRestriction){
			this.spreadMaxConcepts.setMaxConceptsSpreaded( this.maxConceptsRestriction.getConfig().getInit());			
		}else if(restriction instanceof MinConceptsRestriction){
			this.spreadMinConcepts.setMinConceptsSpreaded( this.minConceptsRestriction.getConfig().getInit());			
		}else if(restriction instanceof ActivationRestriction){
			this.spreadMinAct.setMinActivationValue(this.activation.getConfig().getInit());			
		}else if(restriction instanceof ContextRestriction){			
			this.spreadContext.setContext(this.contextRestriction.getContext());
			this.spreadContext.setMaxRetries(this.contextRestriction.getRetries().getInit());
		}else if(restriction instanceof TimeRestriction){
			this.spreadTime.setMaxTime(this.timeRestriction.getConfig().getInit());			
		}
   }
	
	
	public ActivationRestriction getActivation() {
		return activation;
	}

	public void setActivation(ActivationRestriction activation) {
		this.activation = activation;
		this.spreadMinAct = new 	OntoSpreadRestrictionMinActivationValue(this.activation.getConfig().getInit());
	}

	public ContextRestriction getContextRestriction() {
		return contextRestriction;	
	}

	public void setContextRestriction(ContextRestriction contextRestriction) {
		this.contextRestriction = contextRestriction;
		this.spreadContext = new OntoSpreadRestrictionContext(this.contextRestriction.getContext(),this.contextRestriction.getRetries().getInit());
	}

	public MaxConceptsRestriction getMaxConceptsRestriction() {
		return maxConceptsRestriction;
	}

	public void setMaxConceptsRestriction(
			MaxConceptsRestriction maxConceptsRestriction) {
		this.maxConceptsRestriction = maxConceptsRestriction;
		this.spreadMaxConcepts = new OntoSpreadRestrictionMaxConcepts(this.maxConceptsRestriction.getConfig().getInit());
	}

	public MinConceptsRestriction getMinConceptsRestriction() {
		return minConceptsRestriction;
	}

	public void setMinConceptsRestriction(
			MinConceptsRestriction minConceptsRestriction) {
		this.minConceptsRestriction = minConceptsRestriction;
		this.spreadMinConcepts = new OntoSpreadRestrictionMinConcepts(this.minConceptsRestriction.getConfig().getInit());
	}

	public TimeRestriction getTimeRestriction() {
		return timeRestriction;
	}

	public void setTimeRestriction(TimeRestriction timeRestriction) {
		this.timeRestriction = timeRestriction;
		this.spreadTime = new OntoSpreadRestrictionNotMaxTime(this.timeRestriction.getConfig().getInit());
	}
	
	public OntoSpreadCompositeRestriction asStopRestriction(){
		OntoSpreadCompositeRestriction composite = new OntoSpreadCompositeRestriction();
		addRestrictionToComposite(composite,spreadContext);
		addRestrictionToComposite(composite,spreadMaxConcepts);
		addRestrictionToComposite(composite,spreadMinConcepts);
		addRestrictionToComposite(composite,spreadMinAct);
		addRestrictionToComposite(composite,spreadTime);
		return  composite;
	}
	
	private void addRestrictionToComposite(OntoSpreadCompositeRestriction composite, OntoSpreadRestriction restriction){
		if(restriction != null) {			
			composite.getRestrictions().add(restriction);
		}
	}
	
	public OntoSpreadCompositeRestriction asSelectRestriction(){
		OntoSpreadCompositeRestriction composite = new OntoSpreadCompositeRestriction();
		addRestrictionToComposite(composite,spreadContext);
		addRestrictionToComposite(composite,spreadMinAct);
		return  composite;
	}


	public OntoSpreadRestrictionContext getSpreadContext() {
		return spreadContext;
	}


	public void setSpreadContext(OntoSpreadRestrictionContext spreadContext) {
		this.spreadContext = spreadContext;
	}


	public OntoSpreadRestrictionMaxConcepts getSpreadMaxConcepts() {
		return spreadMaxConcepts;
	}


	public void setSpreadMaxConcepts(
			OntoSpreadRestrictionMaxConcepts spreadMaxConcepts) {
		this.spreadMaxConcepts = spreadMaxConcepts;
	}


	public OntoSpreadRestrictionMinActivationValue getSpreadMinAct() {
		return spreadMinAct;
	}


	public void setSpreadMinAct(OntoSpreadRestrictionMinActivationValue spreadMinAct) {
		this.spreadMinAct = spreadMinAct;
	}


	public OntoSpreadRestrictionMinConcepts getSpreadMinConcepts() {
		return spreadMinConcepts;
	}


	public void setSpreadMinConcepts(
			OntoSpreadRestrictionMinConcepts spreadMinConcepts) {
		this.spreadMinConcepts = spreadMinConcepts;
	}


	public OntoSpreadRestrictionNotMaxTime getSpreadTime() {
		return spreadTime;
	}


	public void setSpreadTime(OntoSpreadRestrictionNotMaxTime spreadTime) {
		this.spreadTime = spreadTime;
	}
	
	
	
	public FunctionType getFunctionType() {
		return functionType;
	}


	public void setFunctionType(FunctionType functionType) {		
		this.functionType = functionType;
		if(this.functionType.equals(FunctionType.H_1)){
			this.function =  new OntoSpreadDegradationFunctionImpl();
		}else if(this.functionType.equals(FunctionType.H_2)){
			this.function =  new OntoSpreadDegradationFunctionIterationsImpl();
		}
	}


	public OntoSpreadDegradationFunction getDegradationFunction(){
		return this.function;
	}
	
}
