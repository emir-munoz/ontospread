package org.ontospread.tester;

import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.player.SpreadPlayer;
import org.ontospread.player.SpreadSimplePlayer;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.tester.to.StateDAOPairTO;
import org.ontospread.tester.utils.OntoSpreadTestUtils;
import org.ontospread.tester.xmlbind.ActivationRestriction;
import org.ontospread.tester.xmlbind.ContextRestriction;
import org.ontospread.tester.xmlbind.FunctionType;
import org.ontospread.tester.xmlbind.FunctionTypeList;
import org.ontospread.tester.xmlbind.MaxConceptsRestriction;
import org.ontospread.tester.xmlbind.MinConceptsRestriction;
import org.ontospread.tester.xmlbind.RestrictionType;
import org.ontospread.tester.xmlbind.RestrictionsType;
import org.ontospread.tester.xmlbind.Test;
import org.ontospread.tester.xmlbind.TimeRestriction;
import org.ontospread.to.ScoredConceptTO;
import org.ontospread.to.SpreadedConceptTO;

public class OntoSpreadTestRunner {
	protected static Logger logger = Logger.getLogger(OntoSpreadTestRunner.class);
	OntoSpreadTestLoader loader;
	OntoSpreadTestReporter reporter;
	int iteration;
	public OntoSpreadTestRunner(String testFile, boolean useClasspath) throws Exception{
		try {
			loader = new OntoSpreadTestLoader(testFile, useClasspath);
			reporter = new OntoSpreadTestReporter();
		} catch (Exception e) {
			throw e;
		}
	}	
	public void exec(Test test){
		logger.info("Run test: "+test.getId()+" output directory in "+test.getOutputDirectory());
		List<String> resources = test.getResources().getLocations();
		logger.info("Resources to load "+resources.toString());
		RestrictionsType restrictions = test.getOntoSpreadConfig().getRestrictions();
		RestrictionManagerHelper manager = new RestrictionManagerHelper();
		for (RestrictionType restriction : restrictions.getRestrictions()) {
			addRestriction(manager,restriction);
		}
		iteration = 0;
		//Set function type
		FunctionTypeList functions = test.getOntoSpreadConfig().getFunctions();		
		for (FunctionType function : functions.getFunctions()) {
			manager.setFunctionType(function);			
			logger.info("Function "+manager.getFunctionType());
			for (RestrictionType restriction : restrictions.getRestrictions()) {
				try {
					execIterateRestriction(restriction,test,manager);
				} catch (ConceptNotFoundException e) {
					e.printStackTrace();
				}		
			}
		}
	
	}
	
	private void addRestriction(RestrictionManagerHelper manager, RestrictionType restriction) {
		if(restriction instanceof MaxConceptsRestriction){
			manager.setMaxConceptsRestriction((MaxConceptsRestriction) restriction);		
		}else if(restriction instanceof MinConceptsRestriction){
			manager.setMinConceptsRestriction((MinConceptsRestriction) restriction);			
		}else if(restriction instanceof ActivationRestriction){
			manager.setActivation( (ActivationRestriction) restriction);		
		}else if(restriction instanceof ContextRestriction){
			manager.setContextRestriction((ContextRestriction) restriction);
		}else if(restriction instanceof TimeRestriction){
			manager.setTimeRestriction((TimeRestriction) restriction);
		}
	}
	
	private void execIterateRestriction(RestrictionType restriction, Test test, RestrictionManagerHelper manager) throws ConceptNotFoundException {
		logger.info("::Iterating with restriction "+restriction.getClass().getSimpleName()+" ::");
		//FIXME:refactor: polimorfismo
		if(restriction instanceof MaxConceptsRestriction){			
			while(!manager.hasStopMaxConcepts()){
				StateDAOPairTO state = execute(test, manager);
				showState(state.getOntoSpreadState());
				reportIteration(test, state, manager);
				manager.buildMaxRestriction(true);
			}			
		}else if(restriction instanceof MinConceptsRestriction){
			while(!manager.hasStopMinConcepts()){
				StateDAOPairTO state = execute(test, manager);
				showState(state.getOntoSpreadState());
				reportIteration(test, state, manager);
				manager.buildMinRestriction(true);
			}			
			
		}else if(restriction instanceof ActivationRestriction){
			while(!manager.hasStopAct()){
				StateDAOPairTO state = execute(test, manager);
				showState(state.getOntoSpreadState());
				reportIteration(test, state, manager);
				manager.buildActivationRestriction(true);
			}		
			
		}else if(restriction instanceof ContextRestriction){			
			while(!manager.hasStopContextRetries()){
				StateDAOPairTO state = execute(test, manager);
				showState(state.getOntoSpreadState());
				reportIteration(test, state, manager);
				manager.buildContextRestriction(true);
			}	
		}else if(restriction instanceof TimeRestriction){
			while(!manager.hasStopTime()){
				StateDAOPairTO state = execute(test, manager);
				showState(state.getOntoSpreadState());
				reportIteration(test, state, manager);
				manager.buildTimeRestriction(true);
			}		
			
		}else{
			//Skip
		}
		manager.restart(restriction);
	}
	private void reportIteration(Test test,StateDAOPairTO stateDAOPair, RestrictionManagerHelper manager) {
		try {
			reporter.reportIteration(test, stateDAOPair, manager, iteration++);
		} catch (Exception e) {		
			System.err.println("Error reporting "+test.getId());
			e.printStackTrace(System.err);
		}
		
	}
	private void showState(OntoSpreadState state) {
		/*
		SpreadedConceptTO spreadedConcepts[] = state.getFinalSpreadedConcepts();
		for (int i = 0; i < spreadedConcepts.length; i++) {
			logger.info(spreadedConcepts[i]);
		}
		*/
	}
	private StateDAOPairTO execute(Test test, RestrictionManagerHelper manager) throws ConceptNotFoundException {
		logger.info("Execute");
		OntoSpreadState state = new OntoSpreadState();
		ScoredConceptTO[] initialConcepts = OntoSpreadTestUtils.createScoredConcepts(test.getOntoSpreadConfig().getInitialConcepts());
		state.setInitialConcepts(initialConcepts);
		OntoSpreadRelationWeight relationWeight = OntoSpreadTestUtils.createRelationWeight(test.getOntoSpreadConfig().getRelations().getLocations());
		OntologyDAO ontologyDAO = OntoSpreadTestUtils.createOntologyDAO(test.getResources().getLocations());
		OntoSpreadProcess process = new OntoSpreadProcess(
				ontologyDAO,
				OntoSpreadTestUtils.createDefaultPreAdjustment(test.getOntoSpreadConfig().getInitialConcepts().getValue()), 
				OntoSpreadTestUtils.createDefaultRun(ontologyDAO, manager.asStopRestriction(),manager.asSelectRestriction(),relationWeight, manager.getDegradationFunction()), 
				OntoSpreadTestUtils.createDefaultPost(test.getOntoSpreadConfig().isPrizePaths()),
				relationWeight);		
		SpreadPlayer player = new SpreadSimplePlayer(process,state);
	
		while(player.hasNext()){
			state = player.next();			
		}
		return new StateDAOPairTO(ontologyDAO,state);
	}
	
	private void run(){
		Test test = loader.next();
		while(test != null){
			exec(test);
			test = loader.next();
		}
	}

	
	public static void usage(){
		System.out.println("Usage:");
		System.out.println("OntoSpreadTestRunner file-test.xml");
		System.exit(1);
	}
	
	public static void main(String []args){
		if(args.length != 1){
			usage();
		}
		String testFile = args[0];//args[0]
		OntoSpreadTestRunner testRunner;
		try {
			testRunner = new OntoSpreadTestRunner(testFile, true);
			testRunner.run();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		System.out.println("End of execution "+testFile);	
	}
}
