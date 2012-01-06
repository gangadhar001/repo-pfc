package presentation.dataVisualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.KnowledgeStatus;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.UserRole;

import org.apache.commons.collections15.Transformer;

import presentation.dataVisualization.graph.PopupVertexEdgeMenuMousePlugin;
import presentation.dataVisualization.graph.VertexMenu;
import presentation.panelsActions.panelKnowledgeView;
import resources.ImagesUtilities;
import bussiness.control.ClientController;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
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
import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

/**
 * This class is used to create a custom JUNG Graph. 
 *
 */
public class KnowledgeGraph {

	private VisualizationViewer<Knowledge,String> vv;
	private static Graph<Knowledge, String> graph;
	private static PickedState<Knowledge> pickedState;
	private HashMap<Knowledge, Icon> iconMap;
	private static DefaultVertexIconTransformer<Knowledge> vertexIconTransformer;
	protected static Knowledge selectedVertex;
	private static panelKnowledgeView parent;
	private static ImageIcon iconUser;
	private static ImageIcon iconCountry;
	private static HashMap<Knowledge, Icon> mapCountry = new HashMap<Knowledge, Icon>();
	private static HashMap<Knowledge, Icon> mapUsers = new HashMap<Knowledge, Icon>();
	
	@SuppressWarnings("rawtypes")
	public KnowledgeGraph (TopicWrapper topicWrapper, final panelKnowledgeView parent) {			
		/*** CREATE THE GRAPH ***/
		graph = new DirectedSparseMultigraph<Knowledge, String>();		 
		KnowledgeGraph.parent = parent;
		
		// Map for the Icons
        iconMap = new HashMap<Knowledge,Icon>();
        
        List<Knowledge> knowledgeAttachedFiles = new ArrayList<Knowledge>();
		
        Icon icon = null;
        // Auxiliary count uses to creates edges with unique ID
        int cont = 1;
        // Make vertex and edges
		for (Topic t: topicWrapper.getTopics()) {	
			if (t.getFiles().size() > 0)
				knowledgeAttachedFiles.add(t);
			icon = getIcon("Topic");
			if (icon != null)
				iconMap.put(t, icon);
			
			for (Proposal p: t.getProposals()) {
				if (p.getFiles().size() > 0)
					knowledgeAttachedFiles.add(p);
				icon = getIcon("Proposal");
				if (icon != null)
					iconMap.put(p, icon);
				// Add edge and vertexes
				graph.addEdge(String.valueOf(cont), t, p);
				cont ++;
				
				for (Answer a : p.getAnswers()) {
					if (a.getFiles().size() > 0)
						knowledgeAttachedFiles.add(a);
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
		Dimension preferredSize = new Dimension(700, 700);
		vv = new VisualizationViewer<Knowledge,String>(layout, preferredSize);
				
		/*** SET GRAPH TRANSFORMERS ***/		
		Transformer<Knowledge,Paint> vpf = new PickableVertexPaintTransformer<Knowledge>(vv.getPickedVertexState(), Color.white, Color.yellow);	        
	    vv.getRenderContext().setVertexFillPaintTransformer(vpf);        		 
		 
	    VertexIconShapeTransformer<Knowledge> vertexIconShapeTransformer = new VertexIconShapeTransformer<Knowledge>(new EllipseVertexShapeTransformer<Knowledge>());
	    vertexIconTransformer = new DefaultVertexIconTransformer<Knowledge>();  	        
        vertexIconShapeTransformer.setIconMap(iconMap);
        vertexIconTransformer.setIconMap(iconMap);     
        vv.getRenderContext().setVertexShapeTransformer(vertexIconShapeTransformer);
        vv.getRenderContext().setVertexIconTransformer(vertexIconTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Knowledge>());    
				
		/*** MOUSE EVENTS ***/
		PluggableGraphMouse gm = new PluggableGraphMouse();
		gm.add(new PickingGraphMousePlugin<Knowledge, String>());
		gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
		gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.8f));
		
	    PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        // Add some popup menus for the edges and vertices to mouse plugin.
        JPopupMenu vertexMenu = new VertexMenu<Knowledge>(ClientController.getInstance().getRole());
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
					selectedVertex = (Knowledge) subject;
					if (pickedState.isPicked(selectedVertex)) 
						parent.setKnowledgeSelectedGraph(selectedVertex);
					else
						clearSelection();					
				}
				else
					clearSelection();
			}
		});		
		
		// Show attach file icon to vertex, if any
		for(Knowledge k: knowledgeAttachedFiles)
			setAttachIconVertex(k);
		
		// Show status icon of each vertex
		for(Knowledge k: graph.getVertices()) {
			setStatusIconVertex(k, k.getStatus());
		}
	}

	public static void clearSelection() {
		pickedState.clear();
		parent.setKnowledgeSelectedGraph(null);
		parent.clearSelectionGraph();
	}
	
	private Icon getIcon(String node) {
		 String name = node + ".png";
		 Icon icon = null; 
         try {
        	 icon = new LayeredIcon(ImagesUtilities.loadCompatibleImage("graph/" + name));
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
	
	// Method used to add a new vertex
	public void addVertex(Knowledge k, Knowledge parentK) {
		Random rn = new Random(new Date().getTime());
		int cont = rn.nextInt();
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
			graph.addEdge(k.getTitle() + "_" + String.valueOf(cont), parentK, k);	
		}
		
		vv.repaint();		
	}
	
	// Remove the old vertex and add the new vertex
	public void modifyVertex(Knowledge newK, Knowledge oldK) {	
		Random rn = new Random(new Date().getTime());
		int cont = rn.nextInt();
		Collection<Knowledge> succesors = graph.getSuccessors(oldK);
		Collection<Knowledge> predecessors = graph.getPredecessors(oldK);
		graph.removeVertex(oldK);

		for(Knowledge ob: succesors) {
			graph.addEdge(newK.getTitle() + "_" + String.valueOf(cont), newK, ob);
			cont++;
		}
		for(Knowledge ob: predecessors) {
			graph.addEdge(newK.getTitle() + "_" + String.valueOf(cont), ob, newK);
			cont++;
		}

		clearSelection();
		vv.repaint();
	}
	
	
	public static void deleteVertex() {
	   	// Refresh View
		parent.operationDelete();
	}

	public static void deleteVertexRecursively(Knowledge vertex) {
		Collection<Knowledge> vertexs = graph.getSuccessors(vertex);
	  	 for (Knowledge ob: vertexs) {	   		 
	  		deleteVertexRecursively(ob);
	   	 }   	
	   	graph.removeVertex(vertex);		
	}

	// Method used to attach a new file to a vertex
	public static void attachFile() {
		File file = parent.showAttachFileDialog();
		if (file != null) {
			byte[] bFile = new byte[(int) file.length()];
	   	    FileInputStream fileInputStream;
			try {
				 fileInputStream = new FileInputStream(file);
				 // Convert file into array of bytes
		   	     fileInputStream.read(bFile);
		   	     fileInputStream.close();          
		         model.business.knowledge.File f = new model.business.knowledge.File(file.getName(), bFile);
		         int cont = selectedVertex.getFiles().size();
		         // Insert file into database
		         f.setId(ClientController.getInstance().attachFile(selectedVertex, f));	
		         // Draw the icon
		         if (cont == 0)
		        	 setAttachIconVertex(selectedVertex);
		         selectedVertex.getFiles().add(f);
		         clearSelection();
			} catch (FileNotFoundException e) {
				parent.showMessage(e);
			} catch (IOException e) {
				parent.showMessage(e);
			} catch (SQLException e) {
				parent.showMessage(e);
			} catch (NonPermissionRoleException e) {
				parent.showMessage(e);
			} catch (NotLoggedException e) {
				parent.showMessage(e);
			} catch (Exception e) {
				parent.showMessage(e);
			}	   	     	
		}
	}
	
	// Method used to change the knowledge status
	public static void setStatus(KnowledgeStatus status) {
		selectedVertex.setStatus(status);
		try {
			ClientController.getInstance().changeStatusKnowledge(selectedVertex);
			 // Draw the icon
	         setStatusIconVertex(selectedVertex, status);
		} catch (RemoteException e) {
			parent.showMessage(e);
		} catch (SQLException e) {
			parent.showMessage(e);
		} catch (NonPermissionRoleException e) {
			parent.showMessage(e);
		} catch (NotLoggedException e) {
			parent.showMessage(e);
		} catch (Exception e) {
			parent.showMessage(e);
		}	
	}

	private static void setAttachIconVertex(Knowledge selectedVertex) {
		Icon icon = vertexIconTransformer.transform(selectedVertex);
        if(icon != null && icon instanceof LayeredIcon) {           
        	try {
				((LayeredIcon)icon).add(ImagesUtilities.loadIcon("graph/attach.png"));
			} catch (MalformedURLException e) {
				parent.showMessage(e);
			} catch (IOException e) {
				parent.showMessage(e);
			}           
        }		
	}
	
	private static void setStatusIconVertex(Knowledge selectedVertex, KnowledgeStatus status) {
		Icon icon = vertexIconTransformer.transform(selectedVertex);
        if(icon != null && icon instanceof LayeredIcon) {           
        	try {
        		if (status.equals(KnowledgeStatus.Accepted))
        			((LayeredIcon)icon).add(ImagesUtilities.loadIcon("graph/accept.png"));
        		else if (status.equals(KnowledgeStatus.Rejected))
        			((LayeredIcon)icon).add(ImagesUtilities.loadIcon("graph/reject.png"));
			} catch (MalformedURLException e) {
				parent.showMessage(e);
			} catch (IOException e) {
				parent.showMessage(e);
			}           
        }		
	}
	
	private static void setUserIconVertex(Knowledge selectedVertex) {
		Icon icon = vertexIconTransformer.transform(selectedVertex);
		String role = (selectedVertex.getUser().getRole().name().equals(UserRole.ChiefProject.name()) ? "Chief" : "User");
        if(icon != null && icon instanceof LayeredIcon) {           
        	try {
        		iconUser = ImagesUtilities.loadIcon("graph/" + role + ".png");
        		mapUsers.put(selectedVertex, iconUser); 
				((LayeredIcon)icon).add(iconUser);
			} catch (MalformedURLException e) {
				parent.showMessage(e);
			} catch (IOException e) {
				parent.showMessage(e);
			}           
        }		
	}
	
	private static void setCountryIconVertex(Knowledge selectedVertex) {
		Icon icon = vertexIconTransformer.transform(selectedVertex);
		String country = selectedVertex.getUser().getCompany().getAddress().getCode();
        if(icon != null && icon instanceof LayeredIcon) {           
        	try {
        		iconCountry = ImagesUtilities.loadIcon("graph/" + country + ".png");
        		mapCountry.put(selectedVertex, iconCountry);        		
				((LayeredIcon)icon).add(iconCountry);
			} catch (MalformedURLException e) {
				parent.showMessage(e);
			} catch (IOException e) {
				parent.showMessage(e);
			}           
        }		
	}

	public static void setSelectedVertex(Knowledge selectedVertex) {
		KnowledgeGraph.selectedVertex = selectedVertex;
		parent.setKnowledgeSelectedGraph(selectedVertex);				
	}

	public static Object getSelectedVertex() {
		return selectedVertex;
	}

	public static void clearPickedVertex() {
		pickedState.clear();
		parent.setKnowledgeSelectedGraph(null);		
	}

	public void showAdvancedView(boolean selected) {
		Collection<Knowledge> vertexs = graph.getVertices();
		for(Knowledge v: vertexs) {
			if (selected) {
				setCountryIconVertex(v);
				setUserIconVertex(v);
			}
			else {
				Icon icon = vertexIconTransformer.transform(v);
				((LayeredIcon)icon).remove(mapUsers.get(v));
				((LayeredIcon)icon).remove(mapCountry.get(v));
			}
		}
		
		vv.repaint();
		
	}
	
}
