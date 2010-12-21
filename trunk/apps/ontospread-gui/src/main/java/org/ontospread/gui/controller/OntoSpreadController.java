package org.ontospread.gui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.jpowergraph.Edge;
import net.sourceforge.jpowergraph.Node;
import net.sourceforge.jpowergraph.NodeFilter;
import net.sourceforge.jpowergraph.defaults.ClusterEdge;
import net.sourceforge.jpowergraph.defaults.DefaultGraph;
import net.sourceforge.jpowergraph.defaults.DefaultNodeFilter;
import net.sourceforge.jpowergraph.defaults.LoopEdge;
import net.sourceforge.jpowergraph.defaults.TextEdge;
import net.sourceforge.jpowergraph.example.edges.ArrowEdge;
import net.sourceforge.jpowergraph.example.edges.LineEdge;
import net.sourceforge.jpowergraph.example.edges.LoopEdge2;
import net.sourceforge.jpowergraph.lens.CursorLens;
import net.sourceforge.jpowergraph.lens.LegendLens;
import net.sourceforge.jpowergraph.lens.LensSet;
import net.sourceforge.jpowergraph.lens.NodeSizeLens;
import net.sourceforge.jpowergraph.lens.RotateLens;
import net.sourceforge.jpowergraph.lens.TooltipLens;
import net.sourceforge.jpowergraph.lens.TranslateLens;
import net.sourceforge.jpowergraph.lens.ZoomLens;
import net.sourceforge.jpowergraph.manipulator.dragging.DraggingManipulator;
import net.sourceforge.jpowergraph.manipulator.edge.DefaultEdgeCreatorListener;
import net.sourceforge.jpowergraph.manipulator.edge.EdgeCreatorManipulator;
import net.sourceforge.jpowergraph.manipulator.popup.PopupManipulator;
import net.sourceforge.jpowergraph.manipulator.selection.DefaultNodeSelectionModel;
import net.sourceforge.jpowergraph.manipulator.selection.NodeSelectionListener;
import net.sourceforge.jpowergraph.manipulator.selection.NodeSelectionModel;
import net.sourceforge.jpowergraph.manipulator.selection.SelectionManipulator;
import net.sourceforge.jpowergraph.painters.edge.ArrowEdgePainter;
import net.sourceforge.jpowergraph.painters.edge.ClusterEdgePainter;
import net.sourceforge.jpowergraph.painters.edge.LineEdgePainter;
import net.sourceforge.jpowergraph.painters.edge.LineWithTextEdgePainter;
import net.sourceforge.jpowergraph.painters.edge.LoopEdgePainter;
import net.sourceforge.jpowergraph.painters.node.ShapeNodePainter;
import net.sourceforge.jpowergraph.pane.JGraphPane;
import net.sourceforge.jpowergraph.swtswinginteraction.color.JPowerGraphColor;

import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.gui.utils.OntoSpreadGuiUtils;
import org.ontospread.gui.view.forms.LoadConfigurationForm;
import org.ontospread.gui.view.nodes.info.ConceptNode;
import org.ontospread.gui.view.nodes.info.InstanceNode;
import org.ontospread.gui.view.nodes.info.InstanceOfNode;
import org.ontospread.gui.view.nodes.info.RelationNode;
import org.ontospread.gui.view.nodes.info.SubClassNode;
import org.ontospread.gui.view.nodes.info.SuperClassNode;
import org.ontospread.gui.view.nodes.spread.ActivateNode;
import org.ontospread.gui.view.nodes.spread.ActivationNode;
import org.ontospread.gui.view.nodes.spread.InitialNode;
import org.ontospread.gui.view.nodes.spread.PathNode;
import org.ontospread.gui.view.nodes.spread.SpreadNode;
import org.ontospread.gui.view.nodes.spread.SpreadingNode;
import org.ontospread.player.SpreadDebugPlayer;
import org.ontospread.player.SpreadPlayer;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.RelationTO;
import org.ontospread.to.ScoredConceptTO;

public class OntoSpreadController  implements SpreadPlayer{
	private LensSet lensSet;
	private SpreadPlayer player = null;
	private OntoSpreadProcess ontoSpreadProcess;
	
	public OntoSpreadController() throws ConceptNotFoundException{
		createLenSet();		
	}
	//Constant in all graphs
	private void createLenSet() {
		TranslateLens m_translateLens=new TranslateLens();
		ZoomLens m_zoomLens= new ZoomLens();
		RotateLens m_rotateLens= new RotateLens();
		CursorLens m_cursorLens = new CursorLens();
		TooltipLens m_tooltipLens = new TooltipLens();
		LegendLens m_legendLens = new LegendLens();
		NodeSizeLens m_nodeSizelens = new NodeSizeLens();
		// m_nodeSizelens.setNodeSize(NodePainter.SMALL);
		lensSet = new LensSet();
		lensSet.addLens(m_zoomLens);
		lensSet.addLens(m_rotateLens);
		lensSet.addLens(m_cursorLens);
		lensSet.addLens(m_tooltipLens);
		lensSet.addLens(m_legendLens);
		lensSet.addLens(m_nodeSizelens);
		lensSet.addLens(m_translateLens);
	}

	private OntologyDAO getOntologyDAO() {
		return  this.ontoSpreadProcess.getOntologyDAO();
	}
	
	public DefaultGraph createGraphFromConcept(String conceptUri) throws ConceptNotFoundException{
		return GraphOperations.createGraph(getOntologyDAO().getConcept(conceptUri, null));
	}
	
	public DefaultGraph createGraphFromOntoSpreadState(OntoSpreadState ontoSpreadState) throws Exception{
		return GraphOperations.createGraphFromOntoSpreadState(getOntologyDAO(), ontoSpreadState);
	}

	public DefaultGraph createGraphForCurrentSpreading() throws Exception{
		return GraphOperations.createGraphForSpreading(getOntologyDAO(), current(), current().getConceptToSpread());
	}

	public DefaultGraph createGraphForSpreading(String conceptUri) throws Exception{
		return GraphOperations.createGraphForSpreading(getOntologyDAO(), current(), conceptUri);
	}
	public DefaultGraph createGraphForPath(String conceptUri) throws Exception{
		return GraphOperations.createGraphForPath(getOntologyDAO(), current(), conceptUri);
	}
		
	private void showPathNode(Node currentNode, String currentUri, String[] path, OntologyDAO ontologyDAO,  Map<String, Double> concepts ,  ArrayList <Node> nodes, ArrayList <Edge> edges) throws ConceptNotFoundException {
		for (String concept : path) {			 
			if(!concept.equals(currentUri)){
				ConceptTO conceptPath = ontologyDAO.getConceptTO(concept);
				Node nodePath = new PathNode(conceptPath.getName()+" "+concepts.get(conceptPath.getUri()),conceptPath.getUri());	    		  	
				edges.add(new LineEdge(currentNode,nodePath));
				nodes.add(nodePath);
			}
		}
	}
	public DefaultGraph getSubGraph(Collection <Node> selectedNodes){
		DefaultGraph graph = new DefaultGraph();
		for (Node n : selectedNodes){
			List <Node> nodes = new ArrayList <Node> ();
			List <Edge> edges = new ArrayList <Edge> ();
			nodes.add(n);

			Pair<List <Node>, List<Edge>> p = getParents(n);
			nodes.addAll(p.getFirst());
			edges.addAll(p.getSecond());

			Pair<List <Node>, List<Edge>> c = getDirectChildren(n);
			nodes.addAll(c.getFirst());
			edges.addAll(c.getSecond());

			graph.addElements(nodes, edges);
		}
		return graph;
	}

	public Pair<List <Node>, List<Edge>> getParents(Node theNode){
		List <Node> nodes = new ArrayList <Node> ();
		List <Edge> edges = new ArrayList <Edge> ();
		for (Edge edge : theNode.getEdgesTo()){
			edges.add(edge);
			nodes.add(edge.getFrom());
		}

		return new Pair<List <Node>, List<Edge>>(nodes, edges);
	}

	public Pair<List <Node>, List<Edge>> getDirectChildren(Node theNode){
		List <Node> nodes = new ArrayList <Node> ();
		List <Edge> edges = new ArrayList <Edge> ();
		for (Edge edge : theNode.getEdgesFrom()){
			edges.add(edge);
			nodes.add(edge.getTo());
		}
		return new Pair<List <Node>, List<Edge>>(nodes, edges);
	}

	public void setPainters(JGraphPane theJGraphPane){
		JPowerGraphColor light_blue = new JPowerGraphColor(102, 204, 255);
		JPowerGraphColor dark_blue = new JPowerGraphColor(0, 153, 255);
		JPowerGraphColor light_red = new JPowerGraphColor(255, 102, 102);
		JPowerGraphColor dark_red = new JPowerGraphColor(204, 51, 51);
		JPowerGraphColor light_green = new JPowerGraphColor(153, 255, 102);
		JPowerGraphColor dark_green = new JPowerGraphColor(0, 204, 0);
		JPowerGraphColor black = new JPowerGraphColor(0, 0, 0);
		JPowerGraphColor gray = new JPowerGraphColor(128, 128, 128);

		//Nodes style
		theJGraphPane.setNodePainter(ConceptNode.class, new ShapeNodePainter(ShapeNodePainter.RECTANGLE, light_blue, dark_blue, black));
		theJGraphPane.setNodePainter(SubClassNode.class, new ShapeNodePainter(ShapeNodePainter.RECTANGLE, light_red, dark_red, black));	       
		theJGraphPane.setNodePainter(SuperClassNode.class, new ShapeNodePainter(ShapeNodePainter.RECTANGLE, light_green, dark_green, black));
		theJGraphPane.setNodePainter(RelationNode.class, new ShapeNodePainter(ShapeNodePainter.ELLIPSE, light_blue, dark_blue, black));
		theJGraphPane.setNodePainter(InstanceNode.class, new ShapeNodePainter(ShapeNodePainter.TRIANGLE, light_green, dark_green, black));
		theJGraphPane.setNodePainter(InstanceOfNode.class, new ShapeNodePainter(ShapeNodePainter.ELLIPSE, light_blue, dark_blue, black));
		
		theJGraphPane.setNodePainter(InitialNode.class, new ShapeNodePainter(ShapeNodePainter.RECTANGLE, light_blue, dark_blue, black));	        
		theJGraphPane.setNodePainter(ActivateNode.class, new ShapeNodePainter(ShapeNodePainter.RECTANGLE, light_red, dark_red, black));
		theJGraphPane.setNodePainter(ActivationNode.class, new ShapeNodePainter(ShapeNodePainter.RECTANGLE, light_red, dark_red, black));	       
		theJGraphPane.setNodePainter(SpreadingNode.class, new ShapeNodePainter(ShapeNodePainter.ELLIPSE, light_green, dark_green, black));
		theJGraphPane.setNodePainter(SpreadNode.class, new ShapeNodePainter(ShapeNodePainter.ELLIPSE, light_green, dark_green, black));
		theJGraphPane.setNodePainter(PathNode.class, new ShapeNodePainter(ShapeNodePainter.RECTANGLE, light_blue, dark_blue, black));	       


		//Edges style
		theJGraphPane.setEdgePainter(LoopEdge.class, new LoopEdgePainter(gray, gray, LoopEdgePainter.CIRCULAR));
		theJGraphPane.setEdgePainter(LoopEdge2.class, new LoopEdgePainter(gray, gray, LoopEdgePainter.RECTANGULAR));
		theJGraphPane.setEdgePainter(TextEdge.class, new LineWithTextEdgePainter(gray, gray, false));
		theJGraphPane.setEdgePainter(LineEdge.class, new LineEdgePainter(gray, gray, true));
		theJGraphPane.setEdgePainter(ArrowEdge.class, new ArrowEdgePainter <Edge>());
		theJGraphPane.setEdgePainter(ClusterEdge.class, new ClusterEdgePainter <ClusterEdge>());

		theJGraphPane.setDefaultEdgePainter(new LineEdgePainter(gray, gray, false));
	}


	public NodeFilter getNodeFilter() {
		NodeFilter nodeFilter = new DefaultNodeFilter();
		nodeFilter.addFilterable(SuperClassNode.class, true);
		nodeFilter.addFilterable(SubClassNode.class, true);
		nodeFilter.addFilterable(RelationNode.class, true);
		nodeFilter.addFilterable(InstanceNode.class, true);
		nodeFilter.addFilterable(InstanceOfNode.class, true);

		nodeFilter.addFilterable(InitialNode.class, true);
		nodeFilter.addFilterable(ActivateNode.class, true);
		nodeFilter.addFilterable(ActivationNode.class, true);
		nodeFilter.addFilterable(SpreadingNode.class, true);	
		nodeFilter.addFilterable(SpreadNode.class, true);
		nodeFilter.addFilterable(PathNode.class, true);	
		return nodeFilter;
	}

	public LensSet getLensSet() {
		return lensSet;
	}

	public void addManipulatorsAndSelectionModel(final JGraphPane theJGraphPane) {
		NodeSelectionModel nodeSelectionModel = new DefaultNodeSelectionModel(theJGraphPane.getGraph());
		nodeSelectionModel.addNodeSelectionListener(new NodeSelectionListener() {
			public void selectionCleared(NodeSelectionModel nodeSelectionModel) {
				theJGraphPane.getSubGraphHighlighter().setSubGraph(null);
			}

			public void nodesRemovedFromSelection(NodeSelectionModel nodeSelectionModel, Collection nodes) {
				if (nodeSelectionModel.getSelectedNodes().size() == 0){
					theJGraphPane.getSubGraphHighlighter().setSubGraph(null);
				}
				else{
					theJGraphPane.getSubGraphHighlighter().setSubGraph(getSubGraph(nodeSelectionModel.getSelectedNodes()));
				}
			}

			public void nodesAddedToSelection(NodeSelectionModel nodeSelectionModel, Collection nodes) {
				theJGraphPane.getSubGraphHighlighter().setSubGraph(getSubGraph(nodeSelectionModel.getSelectedNodes()));
			}
		});

		theJGraphPane.addManipulator(new SelectionManipulator(nodeSelectionModel));
		theJGraphPane.addManipulator(new DraggingManipulator((CursorLens) getLensSet().getFirstLensOfType(CursorLens.class)));
		theJGraphPane.addManipulator(new EdgeCreatorManipulator((CursorLens) getLensSet().getFirstLensOfType(CursorLens.class), new DefaultEdgeCreatorListener(theJGraphPane.getGraph())));
		theJGraphPane.addManipulator(new PopupManipulator(theJGraphPane, (TooltipLens) getLensSet().getFirstLensOfType(TooltipLens.class)));
	}


	class Pair <E, F>{

		private E e;
		private F f;

		public Pair (E theE, F theF){
			this.e = theE;
			this.f = theF;
		}

		public E getFirst() {
			return e;
		}

		public F getSecond() {
			return f;
		}
	}

//	private SpreadPlayer createSpreadPlayer() throws ConceptNotFoundException{
//		String []conceptUris = new String[]{
//				"http://websemantica.fundacionctic.org/ontologias/bopa/empleado.owl#Vacaciones",
//				"http://websemantica.fundacionctic.org/ontologias/bopa/ensamble.owl#EmpleadoDeFincas"};
//		OntoSpreadState ontoSpreadState = new OntoSpreadState();
//		ontoSpreadState.setInitialConcepts(OntoSpreadGuiUtils.createScoredConcepts(conceptUris,1.0));
//		return new SpreadDebugPlayer(OntoSpreadGuiUtils.createDefaultOntoSpreadProcess(3, 5, 0.3),
//				ontoSpreadState);
//	}

	public OntoSpreadState first() throws ConceptNotFoundException {			
		return this.player.first();
	}
	public OntoSpreadProcess getOntoSpreadProcess() {			
		return this.ontoSpreadProcess;
	}
	public boolean hasNext() {
		return this.player.hasNext();
	}
	public OntoSpreadState last() throws ConceptNotFoundException {			
		return this.player.last();
	}
	public OntoSpreadState next() throws ConceptNotFoundException {
		return this.player.next();
	}
	public OntoSpreadState previous() throws ConceptNotFoundException {
		return this.player.previous();
	}

	public ConceptTO[] getAllConcepts(){
		return this.ontoSpreadProcess.getOntologyDAO().getAllConcepts();
	}
	
	public String[] getAvailableSpreading() throws ConceptNotFoundException{
		Set <String> keys = current().getSpreadedConcepts();		
		return keys.toArray(new String[keys.size()]);
	}
	
	public String[] getAvailablePaths() throws ConceptNotFoundException{
		Set <String> keys = current().getSpreadPathTable().keySet();		
		return keys.toArray(new String[keys.size()]);
	}

	public DefaultGraph createEmptyGraph(){
		return GraphOperations.createEmptyGraph();
	}
	public OntoSpreadState current() throws ConceptNotFoundException {
		return this.player.current();
	}
	public boolean hasConfig() {
		return ontoSpreadProcess != null;
	}
	public void createProcess(LoadConfigurationForm lcform) throws ConceptNotFoundException {
		this.ontoSpreadProcess =OntoSpreadGuiUtils.createDefaultOntoSpreadProcess(lcform);
		RelationTO[] allRelations = this.ontoSpreadProcess.getOntologyDAO().getAllRelations();
		OntoSpreadRelationWeight relationWeight = this.ontoSpreadProcess.getRelationWeight();
		for (int i = 0; i < allRelations.length; i++) {
			if(
			Double.compare(relationWeight.getWeight(allRelations[i].getUri()),
					relationWeight.getDefault()) == 0){
				relationWeight.setWeight(allRelations[i].getUri(), relationWeight.getDefault());
			}
		}
		
	}
	public void createPlayer(ScoredConceptTO[] initialConcepts) throws ConceptNotFoundException {
		OntoSpreadState ontoSpreadState = new OntoSpreadState();		
		ontoSpreadState.setInitialConcepts(initialConcepts);		
		this.player = new SpreadDebugPlayer(ontoSpreadProcess,ontoSpreadState);
	}
	
	public void clean(){
		this.ontoSpreadProcess = null;
		this.player = null;
	}
	public ScoredConceptTO[] getInitialConcepts() throws ConceptNotFoundException {
		return current().getInitialConcepts();
	}
	public void updateActiveConcepts(ScoredConceptTO[] activeConceptsTOs) throws ConceptNotFoundException {
		Map<String, Double> concepts = current().getConcepts();
		for (int i = 0; i < activeConceptsTOs.length; i++) {
			concepts.put(activeConceptsTOs[i].getConceptUri(), activeConceptsTOs[i].getScore());	
		}
		//Commit
	//	this.player.next();
	//	this.player.previous();
	}
	
	public void updateRelationsWeight(ScoredConceptTO[] relationConceptsTOs) {
		OntoSpreadRelationWeight relationWeight = this.getOntoSpreadProcess().getRelationWeight();
		for (int i = 0; i < relationConceptsTOs.length; i++) {
			relationWeight.setWeight(relationConceptsTOs[i].getConceptUri(), relationConceptsTOs[i].getScore());
		}
	}
	
	

}
