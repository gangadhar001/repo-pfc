package presentation.panelsActions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import model.business.knowledge.Categories;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

import org.jdesktop.application.Application;
import org.jdesktop.swingx.border.DropShadowBorder;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxCellOverlay;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import presentation.JDKnowledge;
import presentation.JFMain;
import presentation.JDPdf;
import presentation.customComponents.DropShadowPanel;
import presentation.dataVisualization.KnowledgeGraph;
import presentation.dataVisualization.TreeContentProvider;
import presentation.dataVisualization.UserInfTable;
import presentation.utils.ImageKnowledgeTreeCellRenderer;
import bussiness.control.ClientController;



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
	
	public panelKnowledgeView(JFMain parent) {
		super();
		this.parent = parent;
		rowSelected = -1;
		try {
			topicWrapper = ClientController.getInstance().getTopicsWrapper();
			initGUI();
			showTree();
			showGraph();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRole e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
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
		
				
		mxHierarchicalLayout layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
		layout.setFineTuning(true);
		layout.setInterRankCellSpacing(230.5);
		layout.setIntraCellSpacing(95.5);
		layout.setUseBoundingBox(false);
		layout.setResizeParent(true);
		
		
		layout.execute(graph.getDefaultParent());
		
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
					Knowledge k = (Knowledge) ((mxCell)cell).getValue(); 
//					topicWrapper.getTopics().get(0).add(new Proposal("adsfasf", "", new Date(), Categories.Analysis));
					showGraph();
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
	
	private void showUserInfo() {
		userInfTable.setValueAt(knowledgeSelectedTree.getUser().getName(), 0, 1);
		userInfTable.setValueAt(knowledgeSelectedTree.getUser().getSurname(), 0, 2);
		userInfTable.setValueAt(knowledgeSelectedTree.getUser().getEmail(), 0, 3);
		String company = knowledgeSelectedTree.getUser().getCompany().getName() + ", " + knowledgeSelectedTree.getUser().getCompany().getAddress().getCountry();
		userInfTable.setValueAt(company, 0, 4);		
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
//			updateBorder(scrollGraph);
			
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateBorder(JComponent comp) {
		 comp.setBorder(BorderFactory.createCompoundBorder(new DropShadowBorder(Color.BLACK, 9, 0.5f, 12, false, false, true, true), comp.getBorder()));
//	        } else {
//	            CompoundBorder border = (CompoundBorder)comp.getBorder();
//	            comp.setBorder(border.getInsideBorder());
//	        }
	}

	/*** Methods used to add or modify knowledge ***/
	public void operationAdd() {
		// TODO: si no hay nada seleccionado, se abre el Frame de conocimiento
		// If an item is selected, show the knowledge window filled with data
		if (knowledgeSelectedTree != null) {
			JDKnowledge fKnowledge = new JDKnowledge("Proposal", knowledgeSelectedTree, "Modify");
			fKnowledge.setLocationRelativeTo(this);
			fKnowledge.setModal(true);
			fKnowledge.setVisible(true);
			TreePath parentPath = tree.getSelectionPath();
//			treeModel.reload();
//			showTree();
			tree.scrollPathToVisible(parentPath);
//			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());			
//			 Topic t = new Topic("title", "desc", new Date());
//			DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
//			treeModel.insertNodeInto(child, parentNode, parentNode.getChildCount());
//			tree.scrollPathToVisible(new TreePath(child.getPath()));
		}
		
	}
}
