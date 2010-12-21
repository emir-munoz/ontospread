package org.ontospread.tester.utils;

import java.util.LinkedList;
import java.util.List;

import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadRelationWeightRDFImpl;
import org.ontospread.dao.JenaOntologyDAOImpl;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.loader.JenaRDFModelWrapper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.model.resources.ExternalizeFilesResourceLoader;
import org.ontospread.model.resources.FilesResourceLoader;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.process.post.OntoSpreadPostAdjustment;
import org.ontospread.process.post.OntoSpreadPostAdjustmentImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfig;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfigImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentImpl;
import org.ontospread.process.run.OntoSpreadDegradationFunction;
import org.ontospread.process.run.OntoSpreadRun;
import org.ontospread.process.run.OntoSpreadRunImpl;
import org.ontospread.restrictions.OntoSpreadRestriction;
import org.ontospread.restrictions.visitor.OntoSpreadBooleanRestrictionVisitor;
import org.ontospread.strategy.OntoSpreadSelectConceptStrategy;
import org.ontospread.strategy.OntoSpreadSimpleStrategy;
import org.ontospread.strategy.OntoSpreadStrategy;
import org.ontospread.strategy.pair.OntoSpreadStrategyVisitorPair;
import org.ontospread.tester.xmlbind.InitialConceptType;
import org.ontospread.tester.xmlbind.InitialConceptsType;
import org.ontospread.to.ScoredConceptTO;

public class OntoSpreadTestUtils {

	public static OntoSpreadStrategyVisitorPair createSelectStrategy(OntoSpreadRestriction restrictions){
		//Restrictions: Strategy Select
		OntoSpreadStrategy selectStrategy = new OntoSpreadSelectConceptStrategy(restrictions);		
		return new OntoSpreadStrategyVisitorPair(selectStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}

	public static OntoSpreadStrategyVisitorPair createStopStrategy(OntoSpreadRestriction restrictions){
		OntoSpreadStrategy stopStrategy = new OntoSpreadSimpleStrategy(restrictions);		
		return new OntoSpreadStrategyVisitorPair(stopStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}

	public static ScoredConceptTO[] createScoredConcepts(InitialConceptsType initialConcepts){
		List <ScoredConceptTO> scoredConcepts = new LinkedList<ScoredConceptTO>();

		for (InitialConceptType initialConcept : initialConcepts.getInitialConcepts()){
			scoredConcepts.add(new ScoredConceptTO(initialConcept.getUri(),initialConcept.getValue()));
		}
		return scoredConcepts.toArray(new ScoredConceptTO[scoredConcepts.size()]);
	}
	public static OntoSpreadPreAdjustment createDefaultPreAdjustment(double value){
		OntoSpreadPreAdjustment pre = new OntoSpreadPreAdjustmentImpl();
		OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig = new OntoSpreadPreAdjustmentConfigImpl(value); 
		pre.setOntoPreAdjustmentConfig(ontoSpreadPreConfig );
		return pre;
	}

	public static OntoSpreadRun createDefaultRun(OntologyDAO ontologyDAO,
			OntoSpreadRestriction restrictionsStop,
			OntoSpreadRestriction restrictionsSelect, 
			OntoSpreadRelationWeight relationWeight, OntoSpreadDegradationFunction function) {	
		return new OntoSpreadRunImpl(
			ontologyDAO, 
			createStopStrategy(restrictionsStop), 
			createSelectStrategy(restrictionsSelect),
			relationWeight,
			function);
		
	}

	public static OntoSpreadPostAdjustment createDefaultPost(boolean makePrize) {
		return new OntoSpreadPostAdjustmentImpl(makePrize);
	}
	
	public static OntoSpreadModelWrapper createOntoModelWrapper(List<String> filenames){
		ResourceLoader resource = new FilesResourceLoader(filenames);
		JenaOWLModelWrapper model = new JenaOWLModelWrapper(resource);
		return model;
	}
	
	public static OntologyDAO createOntologyDAO(List <String>filenames) {		
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}
	
	public static OntoSpreadModelWrapper createModelWrapper(List<String> filesWeights) {
		ResourceLoader resource = new FilesResourceLoader(filesWeights);
		return new JenaRDFModelWrapper(resource);	
		
	}
	
	public static OntoSpreadRelationWeight createRelationWeight(List<String> filesWeights) {
		return new OntoSpreadRelationWeightRDFImpl(createModelWrapper(filesWeights));
	}
}
