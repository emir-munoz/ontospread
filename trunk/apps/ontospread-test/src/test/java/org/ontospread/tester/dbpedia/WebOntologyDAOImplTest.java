package org.ontospread.tester.dbpedia;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.player.SpreadSimplePlayer;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.to.ScoredConceptTO;
import org.ontospread.to.SpreadedConceptTO;
import org.ontospread.utils.ToStringHelper;

import junit.framework.TestCase;

public class WebOntologyDAOImplTest extends TestCase {

	public void testDBPedia() throws ConceptNotFoundException{
		int min = 0; 
		int max = 3;
		double minActivation = 0;
		String context = "";
		int retries = 0;
		long time = 0;		
		double defaultValue = 1.0;		
		OntoSpreadState ontoSpreadState = new OntoSpreadState();	
		String [] initialUris = new String[]{
				"http://dbpedia.org/resource/Berlin",
				"http://dbpedia.org/resource/Quini",
				"http://dbpedia.org/resource/Oviedo"
		};		
		ScoredConceptTO[] initialConcepts = new ScoredConceptTO[initialUris.length];
		for (int i = 0; i < initialUris.length; i++) {
			initialConcepts[i] = new ScoredConceptTO(initialUris[i],1.0);	
		}		
		ontoSpreadState.setInitialConcepts(initialConcepts);		
		String relationsFile = "src/test/resources/dbpedia/dbpedia-weights.rdf";
		String[] resources = new String[0];
		SpreadSimplePlayer player = new SpreadSimplePlayer(OntoSpreadDemoUtils.createDefaultOntoSpreadProcess(resources, relationsFile,
				min,
				max,
				minActivation,
				context,				
				retries, time, defaultValue),	ontoSpreadState);		
		ontoSpreadState = player.next();
		while(player.hasNext()){
			ontoSpreadState = player.next();
		}
		
		System.out.println("::::::::::::::Time "+ontoSpreadState.getSpreadTime()+"ms :::::::::::::::::::");
		SpreadedConceptTO[] finalSpreadedConcepts = ontoSpreadState.getFinalSpreadedConcepts();
		Arrays.sort(finalSpreadedConcepts, new WebOntologyDAOImplTest.CompareFinaSpreadConcepts());
		for (int i = 0; i < finalSpreadedConcepts.length && i<10; i++) {
			System.out.println(finalSpreadedConcepts[i]);			
		}
		//System.out.println(ToStringHelper.arrayToString(finalSpreadedConcepts,"\n"));		
	}
	
	class CompareFinaSpreadConcepts implements Comparator{

		public int compare(Object o1, Object o2) {
			SpreadedConceptTO spreadedConceptTO1  = (SpreadedConceptTO) o1;
			SpreadedConceptTO spreadedConceptTO2  = (SpreadedConceptTO) o2;
			return Double.compare(spreadedConceptTO2.getScore(),spreadedConceptTO1.getScore());
		}
		
	}
	

}
