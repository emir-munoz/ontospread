package org.ontospread.gui.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.jpowergraph.Edge;
import net.sourceforge.jpowergraph.Node;
import net.sourceforge.jpowergraph.defaults.DefaultGraph;
import net.sourceforge.jpowergraph.defaults.TextEdge;
import net.sourceforge.jpowergraph.example.edges.ArrowEdge;
import net.sourceforge.jpowergraph.example.edges.LineEdge;

import org.ontospread.dao.OntologyDAO;
import org.ontospread.gui.view.nodes.PrintNode;
import org.ontospread.gui.view.nodes.info.ConceptNode;
import org.ontospread.gui.view.nodes.info.InstanceNode;
import org.ontospread.gui.view.nodes.info.RelationNode;
import org.ontospread.gui.view.nodes.info.SubClassNode;
import org.ontospread.gui.view.nodes.info.SuperClassNode;
import org.ontospread.gui.view.nodes.spread.ActivateNode;
import org.ontospread.gui.view.nodes.spread.ActivationNode;
import org.ontospread.gui.view.nodes.spread.InitialNode;
import org.ontospread.gui.view.nodes.spread.PathNode;
import org.ontospread.gui.view.nodes.spread.SpreadNode;
import org.ontospread.gui.view.nodes.spread.SpreadingNode;
import org.ontospread.model.builder.OntologyHelper;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.state.UriDepthPair;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.PathTO;
import org.ontospread.to.ScoredConceptTO;
import org.ontospread.xmlbind.Concept;
import org.ontospread.xmlbind.ConceptDescription;
import org.ontospread.xmlbind.Relation;
import org.ontospread.xmlbind.TypeHierarchy;

import resources.ApplicationResources;

public class GraphOperations {
	public static final NumberFormat formatter = new DecimalFormat("#.##");
	
	static String [] relationStrings = {"http://dbpedia.org/property/cityofbirth",
			"http://dbpedia.org/property/deathPlace",
			"http://dbpedia.org/property/birthPlace"
	};
	static Set<String> relations = new HashSet<String>(Arrays.asList(relationStrings));
	
	public static DefaultGraph createGraph(Concept concept){
		DefaultGraph graph = new DefaultGraph();
		ArrayList <Node> nodes = new ArrayList <Node> ();
		ArrayList <Edge> edges = new ArrayList <Edge> ();	     
		Node conceptNode = new ConceptNode(concept.getConceptDescription().getName());
		Node superClassRoot = new PrintNode( ApplicationResources.getString("superclasses.node.root"));
		Node subClassRoot = new PrintNode( ApplicationResources.getString("subclasses.node.root"));
		Node relationsRoot = new PrintNode( ApplicationResources.getString("relations.node.root"));
		Node instancesRoot = new PrintNode( ApplicationResources.getString("instances.node.root"));
		Node instancesOfRoot = new PrintNode( ApplicationResources.getString("instancesOf.node.root"));
		nodes.add(superClassRoot);
		nodes.add(subClassRoot);
		nodes.add(relationsRoot);
		nodes.add(instancesRoot);
		nodes.add(instancesOfRoot);
		edges.add(new LineEdge(conceptNode,superClassRoot));
		edges.add(new LineEdge(conceptNode,subClassRoot));
		edges.add(new LineEdge(conceptNode,instancesRoot));
		edges.add(new LineEdge(conceptNode,relationsRoot));
		edges.add(new LineEdge(conceptNode,instancesRoot));
		edges.add(new LineEdge(conceptNode,instancesOfRoot));
		Node relatedNode = null;
		for (Relation r : concept.getRelations().getRelations()) {
			//For each conceptDescription in relation
			for (ConceptDescription conceptDescription : r.getConceptDescriptions()) {
				final String objectConceptUri = conceptDescription.getUri();
				if(objectConceptUri!=null ){
					if (r.getOnproperty() != null) {
						relatedNode = new RelationNode(conceptDescription.getName());						
						edges.add(new TextEdge(relationsRoot,relatedNode,formatRelation(r.getOnproperty())));						
					} else if (isSuperclassOfRelation(r)) {                      
						relatedNode = new SuperClassNode(conceptDescription.getName());						
						edges.add(new LineEdge(superClassRoot, relatedNode));
						} else {
							// otherwise, the object _is superclass of_ this, so...
							relatedNode = new SubClassNode(conceptDescription.getName());
							edges.add(new LineEdge(subClassRoot, relatedNode));
						}
					nodes.add(relatedNode);
					}
					
			}//End for each concept description in relation
		}//End for each relation
		
		addNodes(concept.getInstances().getConceptDescriptions(), nodes, edges, instancesRoot);
		addNodes(concept.getInstanceof().getConceptDescriptions(),nodes,edges,instancesOfRoot);		
		nodes.add(conceptNode);
		graph.addElements(nodes, edges);
		return graph;
	}

	private static void addNodes(List<ConceptDescription> descriptions, ArrayList<Node> nodes, ArrayList<Edge> edges, Node instancesRoot) {
		Node relatedNode;
		for (ConceptDescription description : descriptions) {
				relatedNode = new InstanceNode(description.getName());
				edges.add(new LineEdge(instancesRoot, relatedNode));
				nodes.add(relatedNode);
		}
	}

	private static boolean isSuperclassOfRelation(Relation r) {
		return r.getHierarchy().equals (TypeHierarchy.SUPERCLASS);
	}
	
	public static String formatActivation(String conceptUri, Double value){
		return conceptUri+" "+formatter.format(value);
	}

	public static DefaultGraph createGraphFromOntoSpreadState(OntologyDAO ontologyDAO, OntoSpreadState ontoSpreadState) throws Exception{
		DefaultGraph graph = new DefaultGraph();
		ArrayList <Node> nodes = new ArrayList <Node> ();
		ArrayList <Edge> edges = new ArrayList <Edge> ();
		//FIXME: Refactor, using relations and draw instances and concepts, equals
		Node root = new PrintNode( ApplicationResources.getString("info.node.root"));
		Node initialRoot = new PrintNode( ApplicationResources.getString("initial.node.root"));
		Map<String, Double> concepts = ontoSpreadState.getConcepts();
		for (ScoredConceptTO initialConcept : ontoSpreadState.getInitialConcepts()) {
			ConceptTO conceptTO = ontologyDAO.getConceptTO(initialConcept.getConceptUri());
			Node node = new InitialNode(formatActivation(conceptTO.getName(),concepts.get(conceptTO.getUri())),conceptTO.getUri());
			edges.add(new LineEdge(initialRoot,node));
			nodes.add(node);
		}

		Node spreadedRoot = new PrintNode( ApplicationResources.getString("initial.node.spreaded"));
		for (String currentSpread : ontoSpreadState.getSpreadedConcepts()) {
			ConceptTO conceptTO = ontologyDAO.getConceptTO(currentSpread);
			Node currentNode = new SpreadNode(formatActivation(conceptTO.getName(),concepts.get(conceptTO.getUri())),conceptTO.getUri());
			edges.add(new LineEdge(spreadedRoot,currentNode));
			nodes.add(currentNode);
		}

		Node activatedRoot = new PrintNode(ApplicationResources.getString("initial.node.activated") );
		for (UriDepthPair activated : ontoSpreadState.getSortedList()) {
			ConceptTO conceptTO = ontologyDAO.getConceptTO(activated.getUri());	
			Node currentNode = new ActivateNode(formatActivation(conceptTO.getName(),concepts.get(conceptTO.getUri())),conceptTO.getUri());
			edges.add(new ArrowEdge(activatedRoot,currentNode));
			nodes.add(currentNode);
		}
		//Creating skeleton of graph
		edges.add(new LineEdge(root,initialRoot));
		edges.add(new LineEdge(root,spreadedRoot));
		edges.add(new LineEdge(root,activatedRoot));
		nodes.add(root);
		nodes.add(initialRoot);
		nodes.add(spreadedRoot);
		nodes.add(activatedRoot);
		graph.addElements(nodes, edges);
		return graph;
	}

	public static DefaultGraph createGraphForSpreading(OntologyDAO ontologyDAO, OntoSpreadState ontoSpreadState, String conceptUri) throws Exception{
		ConceptTO conceptTO = ontologyDAO.getConceptTO(conceptUri);
		return createGraphForSpreading(ontologyDAO, ontoSpreadState, conceptTO);
	}
	
	public static DefaultGraph createGraphForPath(OntologyDAO ontologyDAO, OntoSpreadState ontoSpreadState, String conceptUri) throws Exception{
		ConceptTO conceptTO = ontologyDAO.getConceptTO(conceptUri);
		return createGraphForPath(ontologyDAO, ontoSpreadState, conceptTO);
	}
	
	public static DefaultGraph createGraphForPath(OntologyDAO ontologyDAO, OntoSpreadState ontoSpreadState, ConceptTO conceptTO) throws Exception{
		Map<String, Double> concepts = ontoSpreadState.getConcepts();
		Map<String,PathTO[]> spreadPathTable = ontoSpreadState.getSpreadPathTable();
		DefaultGraph graph = new DefaultGraph();
		ArrayList <Node> nodes = new ArrayList <Node> ();
		ArrayList <Edge> edges = new ArrayList <Edge> ();
		ConceptTO pathConceptTO = null;
		Node spreadedNode = new ActivateNode(formatActivation(conceptTO.getName(),concepts.get(conceptTO.getUri())), conceptTO.getUri());
		Node activatedRoot = new PrintNode(ApplicationResources.getString("initial.node.activated") );
		for (String conceptUri : spreadPathTable.keySet()) {
			PathTO[]path = spreadPathTable.get(conceptUri);
			if(path[path.length-1].getConceptUri().equals(conceptTO.getUri()) && !conceptUri.equals(conceptTO.getUri())){
				Node sonNode = spreadedNode;
				for (int i = path.length-1; i >=0 ; i--) {
					pathConceptTO = ontologyDAO.getConceptTO(path[i].getConceptUri());
					Node pathNode = new PathNode(
							formatActivation(pathConceptTO.getName(),
									concepts.get(pathConceptTO.getUri())),path[i].getConceptUri());
//					for(String relation:path[i].getRelationsUri()){
//						if(relation == null) relation = "#INITIAL";
//						edges.add(new TextEdge(pathNode,sonNode,""+formatRelation(relation)));	
//					}
					edges.add(new LineEdge(pathNode,sonNode));
					nodes.add(pathNode);
					sonNode = pathNode;
				}
				pathConceptTO = ontologyDAO.getConceptTO(conceptUri);				
				Node activateNode = 
					new ActivationNode(
							formatActivation(pathConceptTO.getName(),
									concepts.get(pathConceptTO.getUri())),
									pathConceptTO.getUri());
				for(String relation:path[path.length-1].getRelationsUri()){
					if(relation != null) 
						edges.add(new TextEdge(activatedRoot,activateNode,formatRelation(relation)));	
				}				
				nodes.add(activateNode);
			}//If activated by current spread node
		}
		edges.add(new ArrowEdge(spreadedNode,activatedRoot));
		nodes.add(activatedRoot);
		nodes.add(spreadedNode);
		graph.addElements(nodes, edges);
		return graph;
	}
	
	public static DefaultGraph createGraphForSpreading(OntologyDAO ontologyDAO, OntoSpreadState ontoSpreadState, ConceptTO conceptTO) throws Exception{
		Map<String, Double> concepts = ontoSpreadState.getConcepts();
		Map<String,PathTO[]> spreadPathTable = ontoSpreadState.getSpreadPathTable();
		DefaultGraph graph = new DefaultGraph();
		ArrayList <Node> nodes = new ArrayList <Node> ();
		ArrayList <Edge> edges = new ArrayList <Edge> ();
		ConceptTO pathConceptTO = null;
		Node spreadedNode = new SpreadingNode(formatActivation(conceptTO.getName(),concepts.get(conceptTO.getUri())), conceptTO.getUri());
		Node activatedRoot = new PrintNode(ApplicationResources.getString("initial.node.activated") );
		for (String conceptUri : spreadPathTable.keySet()) {
			PathTO[]path = spreadPathTable.get(conceptUri);
			if(path[path.length-1].getConceptUri().equals(conceptTO.getUri()) && !conceptUri.equals(conceptTO.getUri())){
				Node sonNode = spreadedNode;
				for (int i = path.length-1; i >=0 ; i--) {
					pathConceptTO = ontologyDAO.getConceptTO(path[i].getConceptUri());
					Node pathNode = new PathNode(
							formatActivation(pathConceptTO.getName(),
									concepts.get(pathConceptTO.getUri())),path[i].getConceptUri());
//					for(String relation:path[i].getRelationsUri()){
//						if(relation == null) relation = "#INITIAL";
//						edges.add(new TextEdge(pathNode,sonNode,""+formatRelation(relation)));	
//					}
					edges.add(new LineEdge(pathNode,sonNode));
					nodes.add(pathNode);
					sonNode = pathNode;
				}
				pathConceptTO = ontologyDAO.getConceptTO(conceptUri);				
				Node activateNode = 
					new ActivationNode(
							formatActivation(pathConceptTO.getName(),
									concepts.get(pathConceptTO.getUri())),
									pathConceptTO.getUri());
				for(String relation:path[path.length-1].getRelationsUri()){
					if(relation != null) 
						edges.add(new TextEdge(activatedRoot,activateNode,formatRelation(relation)));	
				}				
				nodes.add(activateNode);
			}//If activated by current spread node
		}
		edges.add(new ArrowEdge(spreadedNode,activatedRoot));
		nodes.add(activatedRoot);
		nodes.add(spreadedNode);
		graph.addElements(nodes, edges);
		return graph;
	}

	private static String formatRelation(String relationUri) {
	//	return formatDBPedia(relationUri);
		return format(relationUri);
	}

	private static String format(String relationUri){
		if(relationUri==null){
			return "";
		}

		if(relationUri.lastIndexOf('#')==-1){
			return relationUri.substring(relationUri.lastIndexOf('/'),relationUri.length());
		}else{
			return OntologyHelper.getSubstringAfterSharp(relationUri);
		}
		
	}
	private static String formatDBPedia(String relationUri){
		return "";
		//return (relations.contains(relationUri))?
			//	relationUri.replaceAll("http://dbpedia.org/property/", ""):"";
	}

	public static DefaultGraph createEmptyGraph() {
		DefaultGraph graph = new DefaultGraph();
		ArrayList <Node> nodes = new ArrayList <Node> ();
		ArrayList <Edge> edges = new ArrayList <Edge> ();
		graph.addElements(nodes, edges);
		return graph;
	}

}
