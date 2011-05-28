package presentation.panelsActions;
import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Operations;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.jdesktop.application.Application;
import org.jdesktop.swingx.border.DropShadowBorder;

import presentation.JDKnowledge;
import presentation.JFMain;
import presentation.customComponents.DropShadowPanel;
import presentation.dataVisualization.KnowledgeGraph;
import presentation.dataVisualization.TreeContentProvider;
import presentation.dataVisualization.UserInfTable;
import presentation.utils.ImageKnowledgeTreeCellRenderer;
import bussiness.control.ClientController;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class panelKnowledgeView extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3144486182937579912L;
	
	private static final double VERTEX_WIDTH = 120;
	
	private JPanel panel;
	private JScrollPane scrollTable;
	private JScrollPane scrollTree;
	private UserInfTable userInfTable;
	private JLabel lblUserInf;
	private JPanel panelTable;
	private JPanel panelGraph;
	private JPanel panelTree;
	protected int rowSelected;
	private TopicWrapper topicWrapper;
	private JTree tree;
	protected Knowledge knowledgeSelectedTree;
	protected int column;
	protected int row;
	private JFMain parent;
	protected DefaultMutableTreeNode parentSelected;
	private DefaultTreeModel treeModel;
	private mxGraph graph;
	private Object parentGraph;

	protected Knowledge knowledgeSelectedGraph;

	private JDKnowledge fKnowledge;
	
	public panelKnowledgeView(JFMain parent) {
		super();
		this.parent = parent;
		rowSelected = -1;
		try {
			// Get knowledge from current project
			topicWrapper = ClientController.getInstance().getTopicsWrapper();
			initGUI();
			// Show knowledge tree and graph
			showTree();
			showGraph();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRole e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	// Method used to show knowledge graph
	private void showGraph() {
		panelGraph.removeAll();
		// Create custom graph
		final mxGraphComponent graphComponent = new mxGraphComponent(new KnowledgeGraph().getGraph());	
		graph = graphComponent.getGraph();
		parentGraph = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		String url;
		String style = "HandleFillColor=#000000;whiteSpace=wrap;overflow=hidden;align=center;strokeColor=#000000;rounded;shape=label;image=";
		// Add nodes and edges
		for (Topic t: topicWrapper.getTopics()) {
			
			url = this.getClass().getClassLoader().getResource("images/Topic.png").toString();			
			mxCell vertexTopic = (mxCell) graph.insertVertex(parentGraph, null, t, 100, 30, 80, 30, style + url);
			vertexTopic.setGeometry(new mxGeometry(vertexTopic.getGeometry().getX(), vertexTopic.getGeometry().getX(), VERTEX_WIDTH, vertexTopic.getGeometry().getHeight()));
			for (Proposal p: t.getProposals()) {
				url = this.getClass().getClassLoader().getResource("images/Proposal.png").toString();
				mxCell vertexProposal = (mxCell) graph.insertVertex(parentGraph, null, p, 100, 50, 80, 30, style + url);
				vertexProposal.setGeometry(new mxGeometry(vertexTopic.getGeometry().getX(), vertexTopic.getGeometry().getX(), VERTEX_WIDTH, vertexTopic.getGeometry().getHeight()));
				graph.insertEdge(parentGraph, null, "", vertexTopic, vertexProposal);
				for (Answer a : p.getAnswers()) {
					url = this.getClass().getClassLoader().getResource("images/Trees/answer.png").toString();
					mxCell vertexAnswer = (mxCell) graph.insertVertex(parentGraph, null, a, 100, 100, 80, 30, style + url);
					vertexAnswer.setGeometry(new mxGeometry(vertexTopic.getGeometry().getX(), vertexTopic.getGeometry().getX(), VERTEX_WIDTH, vertexTopic.getGeometry().getHeight()));
					graph.insertEdge(parentGraph, null, "", vertexProposal, vertexAnswer );
				}
			}
		}
		
		// Set layout
		mxHierarchicalLayout layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
		layout.setFineTuning(true);
		layout.setInterRankCellSpacing(230.5);
		layout.setIntraCellSpacing(95.5);
		layout.setUseBoundingBox(false);
		layout.setResizeParent(true);
		
		
		layout.execute(graph.getDefaultParent());
		
		// Set graph properties
		graph.setAllowDanglingEdges(false);
		graph.setAllowLoops(false);
		graph.setCellsEditable(false);
		graph.setEdgeLabelsMovable(false);
		graph.setCellsDisconnectable(false);
		graph.setCellsBendable(false);
		
		graph.getModel().endUpdate();
		
		graphComponent.setBorder(null);		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{								
			public void mousePressed(MouseEvent e)
			{
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				graph.setSelectionCell(cell);				
				if (cell != null)
				{
					knowledgeSelectedGraph = (Knowledge) ((mxCell)cell).getValue(); 
					showUserInfo();
				}
			}
		});
		graphComponent.setAntiAlias(true);
		graphComponent.setEnabled(true);
		graphComponent.setOpaque(false);
		graphComponent.setToolTips(true);
		panelGraph.add(graphComponent, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 13, 5), 0, 0));

		panelGraph.validate();
		panelGraph.repaint();
	}
	
	// Method used to show knowledge tree
	private void showTree() {	
		panelTree.removeAll();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Knowledge");
		TreeContentProvider.setContentRootNode(root, topicWrapper);
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new ImageKnowledgeTreeCellRenderer());
		tree.addTreeSelectionListener(new TreeSelectionListener() {				
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// Get selected element in the tree
				Object val = ((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject();
				if (!(val instanceof TopicWrapper)) {
					knowledgeSelectedTree = (Knowledge) val;
					showUserInfo();
				}
			}
		});
		scrollTree.setViewportView(tree);
		
		panelTree.add(scrollTree, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
	}
	
	// Method used to display information about the selected knowledge
	private void showUserInfo() {
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
			userInfTable.setValueAt(k.getUser().getName(), 0, 1);
			userInfTable.setValueAt(k.getUser().getSurname(), 0, 2);
			userInfTable.setValueAt(k.getUser().getEmail(), 0, 3);
			String company = k.getUser().getCompany().getName() + ", " + k.getUser().getCompany().getAddress().getCountry();
			userInfTable.setValueAt(company, 0, 4);
		}
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(902, 402));
			this.setName("this");
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1};
			thisLayout.rowHeights = new int[] {7};
			thisLayout.columnWeights = new double[] {0.5, 0.15};
			thisLayout.columnWidths = new int[] {7, 7};
			this.setLayout(thisLayout);
			this.setSize(902, 402);
			{
				panelTree = new JPanel();
				GridBagLayout panelTreeLayout = new GridBagLayout();
				panelTree.setLayout(panelTreeLayout);
				{
					scrollTree = new JScrollPane();
					scrollTree.setPreferredSize(new java.awt.Dimension(61, 18));
					
				}
				this.add(panelTree, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				panelTreeLayout.rowWeights = new double[] {0.1};
				panelTreeLayout.rowHeights = new int[] {7};
				panelTreeLayout.columnWeights = new double[] {0.1};
				panelTreeLayout.columnWidths = new int[] {7};
			}
			{
				panel = new JPanel();
				GridBagLayout panelLayout = new GridBagLayout();
				this.add(panel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
				panelLayout.rowWeights = new double[] {1.0, 0.25};
				panelLayout.rowHeights = new int[] {7, 7};
				panelLayout.columnWeights = new double[] {0.1};
				panelLayout.columnWidths = new int[] {7};
				panel.setLayout(panelLayout);
				{
					panelGraph = new DropShadowPanel();
					GridBagLayout panelGraphLayout = new GridBagLayout();
					panel.add(panelGraph, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
					panelGraph.setLayout(panelGraphLayout);
					panelGraphLayout.rowWeights = new double[] {0.1};
					panelGraphLayout.rowHeights = new int[] {7};
					panelGraphLayout.columnWeights = new double[] {0.1};
					panelGraphLayout.columnWidths = new int[] {7};
				}
				{
					panelTable = new JPanel();
					GridBagLayout panelTableLayout = new GridBagLayout();
					panel.add(panelTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelTableLayout.rowWeights = new double[] {0.05, 1.0};
					panelTableLayout.rowHeights = new int[] {7, 7};
					panelTableLayout.columnWeights = new double[] {0.1};
					panelTableLayout.columnWidths = new int[] {7};
					panelTable.setLayout(panelTableLayout);
					{
						lblUserInf = new JLabel();
						panelTable.add(lblUserInf, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 3, 0), 0, 0));
						lblUserInf.setName("lblUserInf");
					}
					{
						scrollTable = new JScrollPane();
						panelTable.add(scrollTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
						{
							
							userInfTable = new UserInfTable();
							scrollTable.setViewportView(userInfTable);
							DefaultTableModel model = new DefaultTableModel(1, 5);
							userInfTable.setModel(model);
							userInfTable.setShowHorizontalLines(false);
							userInfTable.setShowVerticalLines(false);
							userInfTable.setFillsViewportHeight(true);
							userInfTable.setIntercellSpacing(new Dimension(0,0));
							userInfTable.setPreferredSize(new java.awt.Dimension(631, 32));
							userInfTable.bound();							
							userInfTable.addMouseListener(new MouseListener() {								
								@Override
								public void mouseReleased(MouseEvent e) {									
								}
								
								@Override
								public void mousePressed(MouseEvent e) {
									// If make click over the image, show the company details
									int colClick = userInfTable.getColumnModel().getColumnIndexAtX(e.getX());
									int rowClick = userInfTable.rowAtPoint(e.getPoint());
									if (rowClick == 0 && colClick == 4 && knowledgeSelectedTree != null) {
										TableColumnModel cm = userInfTable.getColumnModel();
										int leftLimit =  cm.getColumn(0).getWidth() + cm.getColumn(1).getWidth() + cm.getColumn(2).getWidth() + cm.getColumn(3).getWidth();
										int rightLimit =  leftLimit + 10;
										// Show dialog with company details
										if (e.getX() >= leftLimit && e.getX() <= rightLimit) { 
											System.out.println("Entra");
											parent.fadeIn(knowledgeSelectedTree.getUser().getCompany());
										}	
									}
								}
								
								@Override
								public void mouseExited(MouseEvent e) {
								}
								
								@Override
								public void mouseEntered(MouseEvent e) {
								}
								
								@Override
								public void mouseClicked(MouseEvent e) {									
								}
							});
						}
					}
				}
			}
			updateBorder(scrollTree);
			updateBorder(scrollTable);
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method used to create a border with shadow
	private void updateBorder(JComponent comp) {
		 comp.setBorder(BorderFactory.createCompoundBorder(new DropShadowBorder(Color.BLACK, 9, 0.5f, 12, false, false, true, true), comp.getBorder()));
	}

	/*** Methods used to add or modify knowledge ***/
	public void operationAdd() {
		// If an item is selected, show the knowledge window filled with data
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
			operationsKnowledge(k.getClass().getSimpleName(), k, Operations.Add.name());
//			TreePath parentPath = tree.getSelectionPath();
//			treeModel.reload();
//			showTree();
//			tree.scrollPathToVisible(parentPath);
//			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());			
//			 Topic t = new Topic("title", "desc", new Date());
//			DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
//			treeModel.insertNodeInto(child, parentNode, parentNode.getChildCount());
//			tree.scrollPathToVisible(new TreePath(child.getPath()));
		}
		//TODO: si no est� seleccionado, que se invoque desde el tollbar o el menu
//		else {
//			operationsKnowledge();
//		}		
	}
	
	// Show the frame of knowledge, used to add or modify knowledge. This frame will has filled with the knowledge data
	private void operationsKnowledge(String knowledgeType, Knowledge k, String operation) {
		fKnowledge = new JDKnowledge(knowledgeType, k, "Add");
		fKnowledge.setLocationRelativeTo(this);
		fKnowledge.setModal(true);
		fKnowledge.setVisible(true);
		// Refresh graph and tree with the new knowledge
//		updateKnowledge(operation);
	}
	
//	private void operationsKnowledge() {
//		fKnowledge = new JDKnowledge();
//		fKnowledge.setLocationRelativeTo(this);
//		fKnowledge.setModal(true);
//		fKnowledge.setVisible(true);
//		updateKnowledge("");
//	}
	
	private void updateKnowledge(String operation) {
			// TODO: se lo dice el servidor el nuevo conocimiento
			
	}

	public void operationDelete() {
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
			try {
				// If the logged user isn't the chief of the project, can only delete if the user is the same as the author's knowledge
				if (!ClientController.getInstance().getLoggedUser().equals(k.getUser())) {
					if (k instanceof Topic)				
						ClientController.getInstance().deleteTopic((Topic) k);
					else if (k instanceof Proposal)				
						ClientController.getInstance().deleteProposal((Proposal) k);
					else			
						ClientController.getInstance().deleteAnswer((Answer) k);
					
					// Refresh knowledge
					topicWrapper = ClientController.getInstance().getTopicsWrapper();
					// Refresh tree and graph
					deleteKnowledgeFromTree(k);
					deleteKnowledgeFromGraph(k);
				}
				else
					// TODO: error
					;
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRole e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	
	private void deleteKnowledgeFromGraph(Knowledge k) {
		mxCell cell = null;
		if (knowledgeSelectedGraph != null)
			 cell = (mxCell) graph.getSelectionCell();
		else
			// Search the cell for the knowledge received
			cell = findCellKnowledge(k);
		if (cell != null) {
			// Get all edges of the vertex
			Object[] edges = graph.getEdges(cell);
			Object[] toRemove = new Object[edges.length];
			for (int i = 0; i< edges.length; i++) {
				toRemove[i] = ((mxCell)edges[i]).getTarget();
			}
			// Remove all edges of that cell
			graph.removeCells(toRemove, true);
			graph.refresh();
			knowledgeSelectedGraph = null;
		}
	}

	// Method used to delete a node from tree
	private void deleteKnowledgeFromTree(Knowledge k) {
		DefaultMutableTreeNode node = null;
		if (knowledgeSelectedTree != null)
			node = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
		else
			// Search the node for the knowledge received 
			node = findNodeKnowledge((DefaultMutableTreeNode)tree.getModel().getRoot(), k);
		if (node != null) {
			DefaultMutableTreeNode aux = (DefaultMutableTreeNode) node.getParent();
			((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
			((DefaultTreeModel)tree.getModel()).reload();
			tree.scrollPathToVisible(new TreePath(aux.getPath()));
			knowledgeSelectedTree = null;
		}
	}

	// Method used to find a vertex in the graph
	private mxCell findCellKnowledge(Knowledge k) {
		mxCell result = null;
		Object[] edges = graph.getAllEdges(new Object[]{graph.getModel().getRoot()});
		boolean found = false;
		for (int i = 0; i< edges.length && !found; i++) {
			if (((Knowledge)((mxCell)edges[i]).getSource().getValue()).equals(k)) {
				result = (mxCell) ((mxCell)edges[i]).getSource();
				found = true;
			}
			else if (((Knowledge)((mxCell)edges[i]).getTarget().getValue()).equals(k)) {
				result = (mxCell) ((mxCell)edges[i]).getTarget();
				found = true;
			}
		}
		return result;
	}
	
	// Method used to find a node in the tree
	@SuppressWarnings("rawtypes")
	private DefaultMutableTreeNode findNodeKnowledge(DefaultMutableTreeNode node, Knowledge k) {
		DefaultMutableTreeNode result = null;
		Enumeration e = node.children();
		boolean found = false;
		while(!found && e.hasMoreElements()) {
			result = (DefaultMutableTreeNode)e.nextElement();
			if (((Knowledge)result.getUserObject()).equals(k))
				found = true;
			else {
				result = findNodeKnowledge(result, k);
				if (result != null)
					found = true;
			}
		}
		return result;
	}

	// Get the selected knowledge (from tree or graph)
	private Knowledge getSelectedKnowledge() {
		Knowledge k = null;
		if (knowledgeSelectedTree != null)
			k = knowledgeSelectedTree;
		else if (knowledgeSelectedGraph != null) {
			k = knowledgeSelectedGraph;
		}
		return k;
	}
}
