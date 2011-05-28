package presentation.panelsActions;
import internationalization.ApplicationInternationalization;
import java.awt.BorderLayout;

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
import javax.swing.JButton;
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
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

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
	private JLabel lblRole;
	private JLabel lblDate;
	private JLabel lblAuthor;
	private JPanel pnlUserInfo;
	private JScrollPane scrollTree;
	private JButton btnDetails;
	private JPanel pnlCompany;
	private JScrollPane jScrollPane1;
	private JLabel lblSeniority;
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
		tree.setPreferredSize(new java.awt.Dimension(125, 411));

		panelTree.add(scrollTree, new AnchorConstraint(13, 1017, 1007, 62, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
	}
	
	// Method used to display information about the selected knowledge
	private void showUserInfo() {
//		Knowledge k = getSelectedKnowledge();
//		if (k != null) {
//			userInfTable.setValueAt(k.getUser().getName(), 0, 1);
//			userInfTable.setValueAt(k.getUser().getSurname(), 0, 2);
//			userInfTable.setValueAt(k.getUser().getEmail(), 0, 3);
//			String company = k.getUser().getCompany().getName() + ", " + k.getUser().getCompany().getAddress().getCountry();
//			userInfTable.setValueAt(company, 0, 4);
//		}
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(1046, 480));
			this.setName("this");
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setSize(1046, 480);
			this.setMinimumSize(new java.awt.Dimension(1046, 480));
			{
				panelTree = new JPanel();
				AnchorLayout panelTreeLayout = new AnchorLayout();
				panelTree.setLayout(panelTreeLayout);
				{
					scrollTree = new JScrollPane();
					scrollTree.setPreferredSize(new java.awt.Dimension(190, 472));

				}
				this.add(getJScrollPane1(), new AnchorConstraint(12, 6, 984, 818, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
				this.add(panelTree, new AnchorConstraint(0, 172, 988, -1, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				panelTree.setPreferredSize(new java.awt.Dimension(180, 474));
			}
			{
				panel = new JPanel();
				AnchorLayout panelLayout = new AnchorLayout();
				this.add(panel, new AnchorConstraint(3, 801, 1009, 177, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				panel.setLayout(panelLayout);
				panel.setPreferredSize(new java.awt.Dimension(661, 481));
				{
					panelGraph = new DropShadowPanel();
					GridBagLayout panelGraphLayout = new GridBagLayout();
					panel.add(panelGraph, new AnchorConstraint(1, 1000, 1001, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					panelGraph.setLayout(panelGraphLayout);
					panelGraph.setPreferredSize(new java.awt.Dimension(615, 481));
					panelGraphLayout.rowWeights = new double[] {0.1};
					panelGraphLayout.rowHeights = new int[] {7};
					panelGraphLayout.columnWeights = new double[] {0.1};
					panelGraphLayout.columnWidths = new int[] {7};
				}
			}
			updateBorder(scrollTree);
			
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
		//TODO: si no está seleccionado, que se invoque desde el tollbar o el menu
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
	
	private JPanel getPnlUserInfo() {
		if(pnlUserInfo == null) {
			pnlUserInfo = new JPanel();
			AnchorLayout pnlUserInfoLayout = new AnchorLayout();
			pnlUserInfo.setLayout(pnlUserInfoLayout);
			pnlUserInfo.setPreferredSize(new java.awt.Dimension(194, 457));
			pnlUserInfo.setBorder(BorderFactory.createTitledBorder(""));
			pnlUserInfo.add(getPnlCompany(), new AnchorConstraint(552, 945, 782, 64, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			pnlUserInfo.add(getLblSeniority(), new AnchorConstraint(493, 523, 526, 64, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			pnlUserInfo.add(getLblRole(), new AnchorConstraint(432, 523, 464, 64, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			pnlUserInfo.add(getLblDate(), new AnchorConstraint(318, 523, 351, 64, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			pnlUserInfo.add(getLblAuthor(), new AnchorConstraint(172, 93, 153, 67, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
		}
		return pnlUserInfo;
	}
	
	private JLabel getLblAuthor() {
		if(lblAuthor == null) {
			lblAuthor = new JLabel();
			lblAuthor.setPreferredSize(new java.awt.Dimension(89, 15));
			lblAuthor.setName("lblAuthor");
		}
		return lblAuthor;
	}
	
	private JLabel getLblDate() {
		if(lblDate == null) {
			lblDate = new JLabel();
			lblDate.setPreferredSize(new java.awt.Dimension(89, 15));
			lblDate.setName("lblDate");
		}
		return lblDate;
	}
	
	private JLabel getLblRole() {
		if(lblRole == null) {
			lblRole = new JLabel();
			lblRole.setPreferredSize(new java.awt.Dimension(89, 15));
			lblRole.setName("lblRole");
		}
		return lblRole;
	}
	
	private JLabel getLblSeniority() {
		if(lblSeniority == null) {
			lblSeniority = new JLabel();
			lblSeniority.setPreferredSize(new java.awt.Dimension(89, 15));
			lblSeniority.setName("lblSeniority");
		}
		return lblSeniority;
	}

	private JScrollPane getJScrollPane1() {
		if(jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new java.awt.Dimension(197, 460));
			jScrollPane1.setViewportView(getPnlUserInfo());
		}
		return jScrollPane1;
	}
	
	private JPanel getPnlCompany() {
		if(pnlCompany == null) {
			pnlCompany = new JPanel();
			AnchorLayout pnlCompanyLayout = new AnchorLayout();
			pnlCompany.setPreferredSize(new java.awt.Dimension(171, 105));
			pnlCompany.setLayout(pnlCompanyLayout);
			pnlCompany.setBorder(BorderFactory.createTitledBorder(""));
			pnlCompany.add(getBtnDetails(), new AnchorConstraint(700, 938, 919, 599, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		}
		return pnlCompany;
	}
	
	private JButton getBtnDetails() {
		if(btnDetails == null) {
			btnDetails = new JButton();
			btnDetails.setPreferredSize(new java.awt.Dimension(58, 23));
			btnDetails.setName("btnDetails");
		}
		return btnDetails;
	}

}
