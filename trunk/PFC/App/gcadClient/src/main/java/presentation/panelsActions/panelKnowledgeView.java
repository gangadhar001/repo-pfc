package presentation.panelsActions;

import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.swingx.border.DropShadowBorder;

import presentation.JDKnowledge;
import presentation.JFMain;
import presentation.customComponents.DropShadowPanel;
import presentation.dataVisualization.KnowledgeGraph;
import presentation.dataVisualization.TreeContentProvider;
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
	private JPanel pnlInfo;
	private JScrollPane scrollTree;
	private JLabel lblCompany;
	private JLabel lblCity;
	private JScrollPane jScrollPane1;
	private JTextArea txtDescription;
	private JLabel lblDescription;
	private JLabel lblIcon;
	private JPanel pnlScrollKnowledge;
	private JScrollPane scrollKnowledge;
	private JPanel pnlScrollUser;
	private JScrollPane jScrollUser;
	private JPanel pnlKnowledgeInfo;
	private JPanel pnlUser;
	private JButton btnDetails;
	private JPanel pnlCompany;
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
					clearSelectionTree();
					knowledgeSelectedGraph = (Knowledge) ((mxCell)cell).getValue(); 
					showUserInfo();
				}
				// No selection
				else {
					clearSelectionTree();
					clearSelectionGraph();
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
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				treeMouseClicked(evt);
			}
		});

		panelTree.add(scrollTree, new AnchorConstraint(13, 1017, 1007, 62, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
	}
	
	// Method used to display information about the selected knowledge
	private void showUserInfo() {
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
			lblAuthor.setText(lblAuthor.getText() + " " + k.getUser().getName() + ", " + k.getUser().getSurname());
		}
		else
			clearUserInfo();
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
				this.add(getPnlUserInfo(), new AnchorConstraint(12, 10, 953, 776, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
				this.add(panelTree, new AnchorConstraint(7, 172, 976, -1, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				panelTree.setPreferredSize(new java.awt.Dimension(180, 461));
			}
			{
				panel = new JPanel();
				AnchorLayout panelLayout = new AnchorLayout();
				this.add(panel, new AnchorConstraint(7, 768, 988, 177, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				panel.setLayout(panelLayout);
				panel.setPreferredSize(new java.awt.Dimension(626, 467));
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
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JPanel getPnlUserInfo() {
		if(pnlInfo == null) {
			pnlInfo = new JPanel();
			AnchorLayout pnlUserInfoLayout = new AnchorLayout();
			pnlInfo.setLayout(pnlUserInfoLayout);
			pnlInfo.setPreferredSize(new java.awt.Dimension(221, 445));
			pnlInfo.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("KV_title_Info")));
			pnlInfo.add(getPnlKnowledgeInfo(), new AnchorConstraint(37, 957, 508, 47, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			pnlInfo.add(getPnlUser(), new AnchorConstraint(232, 10, 112, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
			pnlInfo.add(getPnlCompany(), new AnchorConstraint(762, 952, 962, 47, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		}
		return pnlInfo;
	}
	
	private JPanel getPnlUser() {
		if(pnlUser == null) {
			pnlUser = new JPanel();
			AnchorLayout pnlUserLayout = new AnchorLayout();
			pnlUser.setLayout(pnlUserLayout);
			pnlUser.setPreferredSize(new java.awt.Dimension(201, 101));
			pnlUser.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("KV_title_UserInfo")));
			pnlUser.add(getJScrollUser(), new AnchorConstraint(222, 977, 955, 27, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		}
		return pnlUser;
	}
	
	private JPanel getPnlKnowledgeInfo() {
		if(pnlKnowledgeInfo == null) {
			pnlKnowledgeInfo = new JPanel();
			pnlKnowledgeInfo.setLayout(null);
			pnlKnowledgeInfo.setPreferredSize(new java.awt.Dimension(201, 210));
			pnlKnowledgeInfo.setName("pnlKnowledgeInfo");
			pnlKnowledgeInfo.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("KV_title_KnowledgeInfo")));
			pnlKnowledgeInfo.add(getScrollKnowledge(), new AnchorConstraint(13, 132, 291, 6, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		}
		return pnlKnowledgeInfo;
	}
	
	private JScrollPane getJScrollUser() {
		if(jScrollUser == null) {
			jScrollUser = new JScrollPane();
			jScrollUser.setPreferredSize(new java.awt.Dimension(191, 74));
			jScrollUser.setViewportView(getPnlScrollUser());
		}
		return jScrollUser;
	}
	
	private JPanel getPnlScrollUser() {
		if(pnlScrollUser == null) {
			pnlScrollUser = new JPanel();
			
			pnlScrollUser.setLayout(null);
			pnlScrollUser.add(getLblAuthor());
			pnlScrollUser.add(getLblSeniority());
			pnlScrollUser.add(getLblRole());
		}
		return pnlScrollUser;
	}
	
	private JScrollPane getScrollKnowledge() {
		if(scrollKnowledge == null) {
			scrollKnowledge = new JScrollPane();
			scrollKnowledge.setBounds(5, 21, 191, 183);
			scrollKnowledge.setViewportView(getPnlScrollKnowledge());
		}
		return scrollKnowledge;
	}
	
	private JPanel getPnlScrollKnowledge() {
		if(pnlScrollKnowledge == null) {
			pnlScrollKnowledge = new JPanel();
			
			pnlScrollKnowledge.setLayout(null);
			pnlScrollKnowledge.add(getLblDate());
			pnlScrollKnowledge.add(getLblIcon());
			pnlScrollKnowledge.add(getLblDescription());
			pnlScrollKnowledge.add(getJScrollPane1());
		}
		return pnlScrollKnowledge;
	}
	
	private JLabel getLblIcon() {
		if(lblIcon == null) {
			lblIcon = new JLabel();
			lblIcon.setBounds(56, 7, 77, 44);
		}
		return lblIcon;
	}
	
	private JLabel getLblDescription() {
		if(lblDescription == null) {
			lblDescription = new JLabel();
			lblDescription.setBounds(12, 80, 74, 17);
			lblDescription.setName("lblDescription");
			lblDescription.setText(ApplicationInternationalization.getString("KV_lblDescription"));
		}
		return lblDescription;
	}
	
	private JTextArea getTxtDescription() {
		if(txtDescription == null) {
			txtDescription = new JTextArea();
			txtDescription.setBounds(12, 108, 173, 60);
		}
		return txtDescription;
	}
	
	private JScrollPane getJScrollPane1() {
		if(jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(12, 103, 164, 65);
			jScrollPane1.setViewportView(getTxtDescription());
		}
		return jScrollPane1;
	}
	
	private JLabel getLblCompany() {
		if(lblCompany == null) {
			lblCompany = new JLabel();
			lblCompany.setPreferredSize(new java.awt.Dimension(177, 19));
			lblCompany.setName("lblCompany");
			lblCompany.setText(ApplicationInternationalization.getString("KV_lblCity"));
		}
		return lblCompany;
	}
	
	private JLabel getLblCity() {
		if(lblCity == null) {
			lblCity = new JLabel();
			lblCity.setPreferredSize(new java.awt.Dimension(69, 14));
			lblCity.setName("lblCity");
			lblCity.setText(ApplicationInternationalization.getString("KV_lblCompany"));
		}
		return lblCity;
	}
	
	private JLabel getLblAuthor() {
		if(lblAuthor == null) {
			lblAuthor = new JLabel();
			lblAuthor.setName("lblAuthor");
			lblAuthor.setBounds(12, -1, 185, 22);
			lblAuthor.setText(ApplicationInternationalization.getString("KV_lblAuthor"));
		}
		return lblAuthor;
	}
	
	private JLabel getLblDate() {
		if(lblDate == null) {
			lblDate = new JLabel();
			lblDate.setName("lblDate");
			lblDate.setText(ApplicationInternationalization.getString("KV_lblDate"));
			lblDate.setBounds(12, 58, 101, 16);
		}
		return lblDate;
	}
	
	private JLabel getLblRole() {
		if(lblRole == null) {
			lblRole = new JLabel();
			lblRole.setName("lblRole");
			lblRole.setText(ApplicationInternationalization.getString("KV_lblRole"));
			lblRole.setBounds(12, 48, 100, 16);

		}
		return lblRole;
	}
	
	private JLabel getLblSeniority() {
		if(lblSeniority == null) {
			lblSeniority = new JLabel();
			lblSeniority.setName("lblSeniority");
			lblSeniority.setText(ApplicationInternationalization.getString("KV_lblSeniority"));
			lblSeniority.setBounds(12, 26, 103, 16);
		}
		return lblSeniority;
	}

	private JPanel getPnlCompany() {
		if(pnlCompany == null) {
			pnlCompany = new JPanel();
			AnchorLayout pnlCompanyLayout = new AnchorLayout();
			pnlCompany.setPreferredSize(new java.awt.Dimension(200, 89));
			pnlCompany.setLayout(pnlCompanyLayout);
			pnlCompany.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("KV_title_CompanyInfo")));
			pnlCompany.add(getLblCity(), new AnchorConstraint(488, 407, 646, 62, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			pnlCompany.add(getLblCompany(), new AnchorConstraint(219, 947, 432, 62, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			pnlCompany.add(getBtnDetails(), new AnchorConstraint(634, 952, 904, 537, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		}
		return pnlCompany;
	}
	
	private JButton getBtnDetails() {
		if(btnDetails == null) {
			btnDetails = new JButton();
			btnDetails.setPreferredSize(new java.awt.Dimension(83, 24));
			btnDetails.setName("btnDetails");
			btnDetails.setAction(Application.getInstance().getContext().getActionMap().get("Details"));
			btnDetails.setText(ApplicationInternationalization.getString("btnDetails"));
		}
		return btnDetails;
	}
	
	// Method used to create a border with shadow
	private void updateBorder(JComponent comp) {
		 comp.setBorder(BorderFactory.createCompoundBorder(new DropShadowBorder(Color.BLACK, 9, 0.5f, 12, false, false, true, true), comp.getBorder()));
	}

	/*** Methods used to add, modify or delete knowledge ***/
	public void operationAdd() {
		Object a = tree.getSelectionPath().getLastPathComponent();
		// If an item is selected, show the knowledge window filled with data
		Knowledge k = getSelectedKnowledge();
		if (a != null) {
			operationAddKnowledge(k.getClass().getSimpleName(), k, Operations.Add.name());
		}
	}
	
	public void operationModify() {
		// If an item is selected, show the knowledge window filled with data
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
			try{
				if (!ClientController.getInstance().getLoggedUser().equals(k.getUser())) {
					operationModifyKnowledge(k.getClass().getSimpleName(), k, Operations.Modify.name());
				}
				else
					// TODO: comprobar esto tambi�n el el JFKnowledge
					JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("message_ErrorAuthorModify"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
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
//					// TODO: comprobar esto tambi�n el el JFKnowledge
					JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("message_ErrorAuthorDelete"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
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
	
	// Show the frame of knowledge, used to add. This frame will has filled with the knowledge data
	private void operationAddKnowledge(String knowledgeType, Knowledge k, String operation) {
		fKnowledge = new JDKnowledge(parent, knowledgeType, k, Operations.Add.name());
		fKnowledge.setLocationRelativeTo(this);
		fKnowledge.setModal(true);
		fKnowledge.setVisible(true);
	}
	
	// Show the frame of knowledge, used to modify. This frame will has filled with the knowledge data
	private void operationModifyKnowledge(String knowledgeType, Knowledge k, String operation) {
		fKnowledge = new JDKnowledge(parent, knowledgeType, k, Operations.Modify.name());
		fKnowledge.setLocationRelativeTo(this);
		fKnowledge.setModal(true);
		fKnowledge.setVisible(true);
	}	

	/*** Methods used to notify new knowledge ***/ 
	// Refresh graph and tree, because another client has added knowledge
	public void notifyKnowledgeAdded(model.business.knowledge.Knowledge k) {
		knowledgeAdded(k, null);		
	}

	public void notifyKnowledgeEdited(model.business.knowledge.Knowledge newK, Knowledge oldK) {
		try {
			editKnowledgeFromGraph(newK, oldK);
			editKnowledgeFromTree(newK, oldK);
			// Refresh knowledge
			topicWrapper = ClientController.getInstance().getTopicsWrapper();
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
	
	// Refresh graph and tree, because another client has removed knowledge
	public void notifyKnowledgeRemoved(model.business.knowledge.Knowledge k) {
		try {
			deleteKnowledgeFromGraph(k);
			deleteKnowledgeFromTree(k);
			// Refresh knowledge
			topicWrapper = ClientController.getInstance().getTopicsWrapper();
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
	
	// Refresh graph and tree, because this client has added knowledge
	public void notifyKnowledgeAdded(model.business.knowledge.Knowledge k, Knowledge parentK) {
		knowledgeAdded(k ,parentK);		
	}

	private void knowledgeAdded(Knowledge k, Knowledge parentK) {
		try {
			addKnowledgeToGraph(k, parentK);
			addKnowledgeToTree(k, parentK);
			// Refresh knowledge
			topicWrapper = ClientController.getInstance().getTopicsWrapper();
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

	// Add new knowledge in graph
	private void addKnowledgeToGraph(Knowledge k, Knowledge parentK) {
		mxCell vertexParent = null;
		String style = "HandleFillColor=#000000;whiteSpace=wrap;overflow=hidden;align=center;strokeColor=#000000;rounded;shape=label;image=";
		try {
			if (knowledgeSelectedGraph != null)
				 vertexParent = (mxCell) graph.getSelectionCell();
			else {
				// Search the cell for the parent of knowledge received
				Knowledge parent = null;
				if (parentK == null){
					if (k instanceof Proposal) {
						parent = ClientController.getInstance().findParentProposal((Proposal) k);
						style += this.getClass().getClassLoader().getResource("images/Trees/proposal.png").toString();
					}
					else if (k instanceof Answer) {
						parent = ClientController.getInstance().findParentAnswer((Answer) k);
						style += this.getClass().getClassLoader().getResource("images/Trees/answer.png").toString();
					}
				}
				else
					parent = parentK;
				
				if (parent != null)
					vertexParent = findCellKnowledge(parent);
			}
			if (vertexParent != null) {
				// Add the new knowledge to the vertex parent	
				mxCell newVertex = (mxCell) graph.insertVertex(parentGraph, null, k, 100, 100, 80, 30, style);
				newVertex.setGeometry(new mxGeometry(newVertex.getGeometry().getX(), newVertex.getGeometry().getX(), VERTEX_WIDTH, newVertex.getGeometry().getHeight()));
				graph.insertEdge(parentGraph, null, "", vertexParent, newVertex);				
			}
			// It's a topic
			else if (k instanceof Topic) {
				style += this.getClass().getClassLoader().getResource("images/Trees/topic.png").toString();
				mxCell newVertex = (mxCell) graph.insertVertex(parentGraph, null, k, 100, 100, 80, 30, style);
				newVertex.setGeometry(new mxGeometry(newVertex.getGeometry().getX(), newVertex.getGeometry().getX(), VERTEX_WIDTH, newVertex.getGeometry().getHeight()));	
			}
			graph.refresh();
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

	// Add new knowledge in tree
	private void addKnowledgeToTree(Knowledge k, Knowledge parentK) {
		DefaultMutableTreeNode nodeParent = null;
		try {
			if (knowledgeSelectedTree != null)
				nodeParent = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
			else {
				Knowledge parent = null;
				if (parentK == null){
					// Search the node for the parent of the knowledge received 
					if (k instanceof Proposal) {
						parent = ClientController.getInstance().findParentProposal((Proposal) k);
					}
					else if (k instanceof Answer) {
						parent = ClientController.getInstance().findParentAnswer((Answer) k);
					}
				}
				else
					parent = parentK;
				
				if (parent != null)
					nodeParent = findNodeKnowledge((DefaultMutableTreeNode)tree.getModel().getRoot(), parent);
			}
			if (nodeParent != null) {
				// Add the new knowledge to the parent node	
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(k);
				((DefaultTreeModel)tree.getModel()).insertNodeInto(child, nodeParent, nodeParent.getChildCount());
				((DefaultTreeModel)tree.getModel()).reload();
				tree.scrollPathToVisible(new TreePath(child.getPath()));		
			}
			// It's a topic
			else if (k instanceof Topic) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(k);
				((DefaultTreeModel)tree.getModel()).insertNodeInto(child, (DefaultMutableTreeNode)tree.getModel().getRoot(), ((DefaultMutableTreeNode)tree.getModel().getRoot()).getChildCount());
				((DefaultTreeModel)tree.getModel()).reload();
				tree.scrollPathToVisible(new TreePath(child.getPath()));	
			}
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
	
	// Edit knowledge from graph
	private void editKnowledgeFromGraph(Knowledge newK, Knowledge oldK) {
		mxCell cell = null;
		if (knowledgeSelectedGraph != null)
			 cell = (mxCell) graph.getSelectionCell();
		else
			// Search the cell for the old knowledge received
			cell = findCellKnowledge(oldK);
		if (cell != null) {
			// Replace the old knowledge with the new Knowledge
			cell.setValue(newK);
			graph.refresh();
		}
	}

	// Method used to edit a node from tree
	private void editKnowledgeFromTree(Knowledge newK, Knowledge oldK) {
		DefaultMutableTreeNode node = null;
		if (knowledgeSelectedTree != null)
			node = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
		else
			// Search the node for the knowledge received 
			node = findNodeKnowledge((DefaultMutableTreeNode)tree.getModel().getRoot(), oldK);
		if (node != null) {
			node.setUserObject(newK);
			((DefaultTreeModel)tree.getModel()).reload();
		}
	}
	
	// Delete knowledge from graph
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
		if (k != null) {
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
		}
		return result;
	}
	
	// Method used to find a node in the tree
	@SuppressWarnings("rawtypes")
	private DefaultMutableTreeNode findNodeKnowledge(DefaultMutableTreeNode node, Knowledge k) {
		DefaultMutableTreeNode result = null;
		if (k != null) {
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

	@Action
	public void Details() {
		
	}

	private void treeMouseClicked(MouseEvent evt) {
		int row = tree.getRowForLocation(evt.getX(), evt.getY());
		// Click on empty surface. Clear selection from tree and graph
		if (row == -1) {
			clearSelectionTree();
			clearSelectionGraph();
		}
	}

	// Clear selection from tree
	private void clearSelectionTree() {
		tree.clearSelection();
		knowledgeSelectedTree = null;
		clearUserInfo();
		
	}

	// Clear selection from graph
	private void clearSelectionGraph() {
		graph.clearSelection();
		knowledgeSelectedGraph = null;	
		clearUserInfo();
	}

	// Clear user information
	private void clearUserInfo() {
		
	}
}