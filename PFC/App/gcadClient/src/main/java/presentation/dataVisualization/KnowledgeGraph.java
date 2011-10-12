package presentation.dataVisualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JPopupMenu;

import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.apache.commons.collections15.Transformer;

import presentation.dataVisualization.auxiliary.PopupVertexEdgeMenuMousePlugin;
import presentation.dataVisualization.auxiliary.VertexMenu;
import presentation.panelsActions.panelKnowledgeView;
import resources.ImagesUtilities;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.LayeredIcon;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.decorators.VertexIconShapeTransformer;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class KnowledgeGraph {

	private VisualizationViewer<Knowledge,String> vv;
	private static Graph<Knowledge, String> graph;
	private PickedState<Knowledge> pickedState;
	private HashMap<Knowledge, Icon> iconMap;;
	
	@SuppressWarnings("rawtypes")
	public KnowledgeGraph (TopicWrapper topicWrapper, final panelKnowledgeView parent) {			
		/*** CREATE THE GRAPH ***/
		 graph = new DirectedSparseMultigraph<Knowledge, String>();
		 
		 
		// Map for the Icons
        iconMap = new HashMap<Knowledge,Icon>();
		
        Icon icon = null;
        // Auxiliary count uses to creates edges with unique ID
        int cont = 1;
        // Make vertex and edges
		for (Topic t: topicWrapper.getTopics()) {		
			icon = getIcon("Topic");
			if (icon != null)
				iconMap.put(t, icon);
			
			for (Proposal p: t.getProposals()) {
				icon = getIcon("Proposal");
				if (icon != null)
					iconMap.put(p, icon);
				// Add edge and vertexes
				graph.addEdge(String.valueOf(cont), t, p);
				cont ++;
				
				for (Answer a : p.getAnswers()) {
					icon = getIcon("Answer");
					if (icon != null)
						iconMap.put(a, icon);
					// Add edge and vertexes
					graph.addEdge(String.valueOf(cont), p, a);
					cont++;
				}
			}
		}
		
		/*** SET LAYOUT ***/
		// The Layout<V, E> is parameterized by the vertex and edge types
		FRLayout<Knowledge, String> layout = new FRLayout<Knowledge, String>(graph);
		layout.setMaxIterations(100);	
		
		/*** SET GRAPH VISUALIZATION ***/
		vv = new VisualizationViewer<Knowledge,String>(layout);
				
		/*** SET GRAPH TRANSFORMERS ***/		
		Transformer<Knowledge,Paint> vpf = new PickableVertexPaintTransformer<Knowledge>(vv.getPickedVertexState(), Color.white, Color.yellow);	        
	    vv.getRenderContext().setVertexFillPaintTransformer(vpf);        		 
		 
	    VertexIconShapeTransformer<Knowledge> vertexIconShapeTransformer = new VertexIconShapeTransformer<Knowledge>(new EllipseVertexShapeTransformer<Knowledge>());
	    DefaultVertexIconTransformer<Knowledge> vertexIconTransformer = new DefaultVertexIconTransformer<Knowledge>();  	        
        vertexIconShapeTransformer.setIconMap(iconMap);
        vertexIconTransformer.setIconMap(iconMap);     
        vv.getRenderContext().setVertexShapeTransformer(vertexIconShapeTransformer);
        vv.getRenderContext().setVertexIconTransformer(vertexIconTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Knowledge>());    
				
		/*** MOUSE EVENTS ***/
		PluggableGraphMouse gm = new PluggableGraphMouse();
//		gm.add(new PickingGraphMousePlugin<Knowledge, String>());
		gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
		gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
		
	    PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        // Add some popup menus for the edges and vertices to mouse plugin.
        JPopupMenu vertexMenu = new VertexMenu();
        myPlugin.setVertexPopup(vertexMenu);       
        gm.add(myPlugin);        
		
		vv.setGraphMouse(gm);
		
		pickedState = vv.getPickedVertexState();
		// Listener uses to get the selected vertex
		pickedState.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object subject = e.getItem();
				// The graph uses Knowledge for vertices
				if (subject instanceof Knowledge) {		
					Knowledge vertex = (Knowledge) subject;
					if (pickedState.isPicked(vertex)) 
						parent.setKnowledgeSelectedGraph(vertex);
					else
						parent.setKnowledgeSelectedGraph(null);					
				}
			}
		});		
	}

	public void clearSelection() {
		pickedState.clear();
	}
	
	private Icon getIcon(String node) {
		 String name = node + ".png";
		 Icon icon = null; 
         try {
        	 icon = new LayeredIcon(ImagesUtilities.loadCompatibleImage(name));
         } catch(Exception ex) { }	
         return icon;
	}

	public Graph<Knowledge, String> getGraph() {
		return graph;
	}

	public void setGraph(Graph<Knowledge, String> graph) {
		KnowledgeGraph.graph = graph;
	}

	public VisualizationViewer<Knowledge, String> getVisualGraph() {
		return vv;
	}

	public void setVisualGraph(VisualizationViewer<Knowledge, String> vv) {
		this.vv = vv;
	}		
	
	public void addVertex(Knowledge k, Knowledge parentK) {
		int cont = graph.getEdgeCount() + 1;
		Icon icon = null;
		if (k instanceof Topic)
			icon = getIcon("Topic");
		else if (k instanceof Proposal)
			icon = getIcon("Proposal");
		else if (k instanceof Answer)
			icon = getIcon("Answer");
		if (icon != null)
			iconMap.put(k, icon);	
		
		// It's a topic, so "k" doesn't have knowledge parent
		if (parentK == null) {		
			// Add vertex
			graph.addVertex(k);		
		}
		else {
			graph.addEdge(String.valueOf(cont), parentK, k);	
		}
		
		vv.repaint();		
	}
	
	public void modifyVertex(Knowledge newK, Knowledge oldK) {
		Collection<Knowledge> vertexs = graph.getVertices();
		for(Knowledge o : vertexs) {
			if (o.equals(oldK)) {
				// Replace the old knowledge
				oldK = (Knowledge) newK.clone();
			}
		}
		
//		int cont = graph.getEdgeCount() + 1;
//		Collection<Knowledge> vertexs = graph.getSuccessors(oldK);
//		graph.removeVertex(oldK);
//		for(Object ob: vertexs) {
//			graph.addEdge(String.valueOf(cont), newK, (Knowledge) ob);
//			cont++;
//		}
		
		vv.repaint();
	}
	
	// Method use to delete, recursively, a vertex and its connections
	public static void deleteVertex(Knowledge vertex) {
		Collection<Knowledge> vertexs = graph.getSuccessors(vertex);
	   	 for (Knowledge ob: vertexs) {	   		 
	   		deleteVertex(ob);
	   	 }   	
	   	graph.removeVertex(vertex);
	}
	
}
