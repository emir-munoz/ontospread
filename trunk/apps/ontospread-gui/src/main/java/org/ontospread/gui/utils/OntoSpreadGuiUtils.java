package org.ontospread.gui.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.sourceforge.jpowergraph.swtswinginteraction.color.JPowerGraphColor;

import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadRelationWeightImpl;
import org.ontospread.constraints.OntoSpreadRelationWeightRDFImpl;
import org.ontospread.gui.utils.DAOUtils;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.gui.view.forms.LoadConfigurationForm;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.process.post.OntoSpreadPostAdjustment;
import org.ontospread.process.post.OntoSpreadPostAdjustmentImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfig;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfigImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentImpl;
import org.ontospread.process.run.OntoSpreadRun;
import org.ontospread.process.run.OntoSpreadRunImpl;
import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionContext;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinActivationValue;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionNotMaxTime;
import org.ontospread.restrictions.visitor.OntoSpreadBooleanRestrictionVisitor;
import org.ontospread.strategy.OntoSpreadSelectConceptStrategy;
import org.ontospread.strategy.OntoSpreadSimpleStrategy;
import org.ontospread.strategy.OntoSpreadStrategy;
import org.ontospread.strategy.pair.OntoSpreadStrategyVisitorPair;
import org.ontospread.to.ScoredConceptTO;

public class OntoSpreadGuiUtils {

	private final static Random rand = new Random();
	
	public static OntoSpreadStrategyVisitorPair createSelectStrategy(){
		//Restrictions: Strategy Select
		OntoSpreadCompositeRestriction restrictionsSelect = new OntoSpreadCompositeRestriction();
		restrictionsSelect.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(10.0));
		OntoSpreadStrategy selectStrategy = new OntoSpreadSelectConceptStrategy(restrictionsSelect);		
		return new OntoSpreadStrategyVisitorPair(selectStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}

	public static OntoSpreadStrategyVisitorPair createStopStrategy(
			int min, 
			int max, 
			double minActivation,
			String context,
			int retries,
			long time){
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		if(min!= 0) restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(min));
		if(max!= 0) restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(max));	
		if(minActivation != 0)restrictions.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(minActivation));
		if(context!=null && !context.equals("") && retries!=0)restrictions.getRestrictions().add(new OntoSpreadRestrictionContext(context,retries));		
		if(time != 0) restrictions.getRestrictions().add(new OntoSpreadRestrictionNotMaxTime(time));
		OntoSpreadStrategy stopStrategy = new OntoSpreadSimpleStrategy(restrictions);		
		return new OntoSpreadStrategyVisitorPair(stopStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}

	public static OntoSpreadStrategyVisitorPair createStopStrategy(
			int min, 
			int max, 
			double minActivation){
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(min));
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(max));	
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(minActivation));
		OntoSpreadStrategy stopStrategy = new OntoSpreadSimpleStrategy(restrictions);		
		return new OntoSpreadStrategyVisitorPair(stopStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}
	
	public static ScoredConceptTO[] createScoredConcepts(String [] uris, double initialScore){
		List <ScoredConceptTO> scoredConcepts = new LinkedList<ScoredConceptTO>();
		for(int i = 0; i<uris.length;i++){
			scoredConcepts.add(new ScoredConceptTO(uris[i],initialScore));
		}
		return scoredConcepts.toArray(new ScoredConceptTO[uris.length]);
	}

	public static OntoSpreadPreAdjustment createDefaultPreAdjustment(){
		OntoSpreadPreAdjustment pre = new OntoSpreadPreAdjustmentImpl();
		OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig = new OntoSpreadPreAdjustmentConfigImpl(10.0);
		pre.setOntoPreAdjustmentConfig(ontoSpreadPreConfig );
		return pre;
	}

	public static OntoSpreadRun createDefaultRun(
			int min, 
			int max, 
			double minActivation,
			String context,
			int retries,
			long time,			
			OntologyDAO ontologyDAO) {	
		return new OntoSpreadRunImpl(
			ontologyDAO, 
			createStopStrategy(min,max, minActivation,context,retries,time), 
			createSelectStrategy());
		
	}

	private static OntoSpreadRelationWeight createRelationWeight(String filename, double defaultValue) {
		if(filename == null || filename.equals("")){
			return new OntoSpreadRelationWeightImpl();
		}
		OntoSpreadRelationWeightRDFImpl ontoSpreadRelationWeightRDFImpl = new OntoSpreadRelationWeightRDFImpl(
						DAOUtils.createModelWrapper(new String[]{filename}));
		ontoSpreadRelationWeightRDFImpl.setWeight(OntoSpreadRelationWeight.DEFAULT_URI, defaultValue);
		return ontoSpreadRelationWeightRDFImpl;
	}

	public static OntoSpreadPostAdjustment createDefaultPost() {
		return new OntoSpreadPostAdjustmentImpl();
	}

	//Generic ontoSpreadProcess create
	public static OntoSpreadProcess createDefaultOntoSpreadProcess(LoadConfigurationForm lcform){			
		//OntologyDAO ontologyDAO = DAOUtils.createDBPediaOntologyDAO(lcform.getResources());
		OntologyDAO ontologyDAO = DAOUtils.createOntologyDAO(lcform.getResources());
		OntoSpreadPreAdjustment createDefaultPreAdjustment = createDefaultPreAdjustment();
		return new OntoSpreadProcess(
				ontologyDAO,
				createDefaultPreAdjustment, 
				createDefaultRun(lcform.getMinConcepts(),
						lcform.getMaxConcepts(),
						lcform.getActivationValue(),
						lcform.getContext(),
						lcform.getRetries(),
						lcform.getTime(),
						ontologyDAO), 
				createDefaultPost(),
				createRelationWeight(lcform.getRelationsFile(),lcform.getDefaultValue())); 
	}

	public static JPowerGraphColor randomColor(){		
        return new JPowerGraphColor(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
    }
    
}
