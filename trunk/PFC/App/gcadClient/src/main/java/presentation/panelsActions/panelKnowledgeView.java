package presentation.panelsActions;

import internationalization.ApplicationInternationalization;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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

import org.japura.gui.CollapsiblePanel;
import org.japura.gui.CollapsibleRootPanel;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.JDKnowledge;
import presentation.JFMain;
import presentation.customComponents.ImagePanel;
import presentation.dataVisualization.KnowledgeGraph;
import presentation.dataVisualization.TreeContentProvider;
import presentation.utils.ImageKnowledgeTreeCellRenderer;
import resources.ImagesUtilities;
import bussiness.control.ClientController;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import exceptions.NonPermissionRoleException;
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
public class panelKnowledgeView extends ImagePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3144486182937579912L;

	private JPanel pnlInfo;
	private JScrollPane scrollTree;
	private CollapsiblePanel collapsiblePanel3;
	private CollapsiblePanel collapsiblePanel1;
	private CollapsibleRootPanel collapsibleRootPanel1;
	private CollapsiblePanel collapsiblePanel;
	private GraphZoomScrollPane panelGraph;
	private JPanel panelTree;
	private TopicWrapper topicWrapper;
	private JTree tree;
	private Knowledge knowledgeSelectedTree;
	private JFMain parent;
	private DefaultTreeModel treeModel;

	private Knowledge knowledgeSelectedGraph;
	private JDKnowledge fKnowledge;
	private KnowledgeGraph KnowGraph;
	
	
	public panelKnowledgeView(JFMain parent) {
		super();
		this.parent = parent;
		try {
			super.setImage(ImagesUtilities.loadCompatibleImage("background.jpg"));
		} catch (Exception e) {}
		try {		
			// Get knowledge from current project
			topicWrapper = ClientController.getInstance().getTopicsWrapper();
			// Create custom graph
			KnowGraph = new KnowledgeGraph(topicWrapper, this);
			
			initGUI();
			// Show knowledge tree and graph			
			showTree();
//			showGraph();
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
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	// Method used to show knowledge graph
	private void showGraph() {
		panelGraph.removeAll();			
		
		panelGraph.add(KnowGraph.getVisualGraph(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 13, 5), 0, 0));
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
		tree.setBorder(new EmptyBorder(5, 5, 5, 5));
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
					// Enable delete an edit buttons in toolbar
					activateToolbarButtons(true);
				}
			}
		});
		scrollTree.setViewportView(tree);
		tree.setPreferredSize(new java.awt.Dimension(184, 547));
		tree.setName("tree");
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				treeMouseClicked(evt);
			}
		});

		panelTree.add(scrollTree, new AnchorConstraint(0, 947, 1000, 2, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
	}
	
	// Method used to display information about the selected knowledge
	private void showUserInfo() {
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
//			lblAuthor.setText(lblAuthor.getText() + " " + k.getUser().getName() + ", " + k.getUser().getSurname());
		}
		else
			clearUserInfo();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(1008, 601));
			this.setName("this");
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setSize(1008, 601);
			this.setMinimumSize(new java.awt.Dimension(1008, 601));
			{
				panelGraph = new GraphZoomScrollPane(KnowGraph.getVisualGraph());
				this.add(panelGraph, new AnchorConstraint(12, 779, 974, 229, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				panelGraph.setPreferredSize(new java.awt.Dimension(556, 573));
			}
			{
				panelTree = new JPanel();
				AnchorLayout panelTreeLayout = new AnchorLayout();
				panelTree.setLayout(panelTreeLayout);
				{
					scrollTree = new JScrollPane();
					scrollTree.setPreferredSize(new java.awt.Dimension(205, 573));

				}
				this.add(getPnlUserInfo(), new AnchorConstraint(12, 10, 974, 791, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
				this.add(panelTree, new AnchorConstraint(12, 172, 974, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				panelTree.setPreferredSize(new java.awt.Dimension(217, 573));
				panelTree.setName("panelTree");
			}
			activateToolbarButtons(false);
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
			pnlInfo.setPreferredSize(new java.awt.Dimension(201, 573));
			pnlInfo.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("KV_title_Info")));
			pnlInfo.add(getCollapsibleRootPanel1(), new AnchorConstraint(75, 957, 978, 10, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
		}
		return pnlInfo;
	}

	/*** Methods used to add, modify or delete knowledge, since toolbar ***/
	public void operationAdd() {
		// If an item is selected, show the knowledge window filled with data
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
			operationKnowledge(k.getClass().getSimpleName(), k, Operations.Add.name());
		}
	}
	
	public void operationModify() {
		// If an item is selected, show the knowledge window filled with data
		Knowledge k = getSelectedKnowledge();
		if (k != null) {
			try{
				// Only the author can modify its knowledge
				if (ClientController.getInstance().getLoggedUser().equals(k.getUser())) {
					operationKnowledge(k.getClass().getSimpleName(), k, Operations.Modify.name());
				}
				else
					JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("message_ErrorAuthorModify"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
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
				if (ClientController.getInstance().getLoggedUser().equals(k.getUser())) {
					if (k instanceof Topic)				
						ClientController.getInstance().deleteTopic((Topic) k);
					else if (k instanceof Proposal)				
						ClientController.getInstance().deleteProposal((Proposal) k);
					else			
						ClientController.getInstance().deleteAnswer((Answer) k);
					
					notifyKnowledgeRemoved(k);
				}
				else
					JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("message_ErrorAuthorDelete"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
	
	// Show the frame of knowledge, used to add or modify. This frame will has filled with the knowledge data
	private void operationKnowledge(String knowledgeType, Knowledge k, String operation) {
		fKnowledge = new JDKnowledge(parent, knowledgeType, k, operation);
		fKnowledge.setLocationRelativeTo(parent.getMainFrame());
		fKnowledge.setModal(true);
		fKnowledge.setVisible(true);
	}
	
	// Invoke JDKnowledge without arguments (no operation, no data)
	public void manageKnowledgeFromMenu() {	
		JDKnowledge frameKnowledge = new JDKnowledge(parent);
		frameKnowledge.setLocationRelativeTo(parent.getMainFrame());
		frameKnowledge.setModal(true);
		frameKnowledge.setVisible(true);		
	}

	/*** Methods used to notify new knowledge ***/ 
	// Refresh graph and tree, because another client has added knowledge
//	public void notifyKnowledgeAdded(Knowledge k) {
//		knowledgeAdded(k, null);		
//	}

	// Refresh graph and tree
	public void notifyKnowledgeEdited(Knowledge newK, Knowledge oldK) {
		refreshKnowledge();			
		editKnowledgeFromGraph(newK, oldK);
		editKnowledgeFromTree(newK, oldK);	
		clearSelectionTree();
		clearSelectionGraph();
	}
	
	// Refresh graph and tree
	public void notifyKnowledgeRemoved(Knowledge k) {
		refreshKnowledge();
		deleteKnowledgeFromGraph(k);
		deleteKnowledgeFromTree(k);	
		clearSelectionTree();
		clearSelectionGraph();
	}
	
	// Refresh graph and tree, because this client has added knowledge
	public void notifyKnowledgeAdded(Knowledge k, Knowledge parentK) {
		refreshKnowledge();
		addKnowledgeToGraph(k, parentK);
		addKnowledgeToTree(k, parentK);
		clearSelectionTree();
		clearSelectionGraph();
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
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	

	// Add new knowledge in graph
	private void addKnowledgeToGraph(Knowledge k, Knowledge parentK) {               
	    // Add the new knowledge 
		KnowGraph.addVertex(k, parentK);                        
	}

	// Method used to edit a node from tree
	private void editKnowledgeFromTree(Knowledge newK, Knowledge oldK) {
		// TODO: controlar si cambia el padre del conocimiento
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
	
	// Edit knowledge from graph
	private void editKnowledgeFromGraph(Knowledge newK, Knowledge oldK) {
		// TODO: controlar si cambia el padre del conocimiento
		KnowGraph.modifyVertex(newK, oldK);
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
	
	// Delete knowledge from graph
	private void deleteKnowledgeFromGraph(Knowledge k) {
		KnowledgeGraph.deleteVertexRecursively(k);		
	}
	
	private void refreshKnowledge() {
		// Refresh knowledge
		try {
			topicWrapper = ClientController.getInstance().getTopicsWrapper();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}		
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
			parent.enableToolbarButton("ModifyKnowledge", false);
			parent.enableToolbarButton("DeleteKnowledge", false);
		}
	}

	// Clear selection from tree
	private void clearSelectionTree() {
		tree.clearSelection();
		knowledgeSelectedTree = null;
		clearUserInfo();
		activateToolbarButtons(false);
	}

	// Clear selection from graph
	private void clearSelectionGraph() {
		KnowGraph.clearSelection();
		knowledgeSelectedGraph = null;	
		clearUserInfo();
		activateToolbarButtons(false);
	}

	// Clear user information
	private void clearUserInfo() {
		
	}

	private CollapsiblePanel getCollapsiblePanel() {
		if(collapsiblePanel == null) {
			collapsiblePanel = new CollapsiblePanel();			
			collapsiblePanel.setAutoscrolls(true);
			collapsiblePanel.setTitle("Company");
			collapsiblePanel.setBounds(6, 12, 189, 162);
		}
		return collapsiblePanel;
	}

	private CollapsibleRootPanel getCollapsibleRootPanel1() {
		if(collapsibleRootPanel1 == null) {
			collapsibleRootPanel1 = new CollapsibleRootPanel(CollapsibleRootPanel.SCROLL_BAR);
			collapsibleRootPanel1.setAutoscrolls(true);
			collapsibleRootPanel1.setPreferredSize(new java.awt.Dimension(201, 392));
			collapsibleRootPanel1.add(getCollapsiblePanel());
			collapsibleRootPanel1.add(getCollapsiblePanel1());
			collapsibleRootPanel1.add(getCollapsiblePanel3());
			collapsibleRootPanel1.setMaxHeight(418);
		}
		return collapsibleRootPanel1;
	}

	private CollapsiblePanel getCollapsiblePanel1() {
		if(collapsiblePanel1 == null) {
			collapsiblePanel1 = new CollapsiblePanel();
			collapsiblePanel1.setBounds(6, 180, 189, 117);
			collapsiblePanel1.setName("collapsiblePanel1");
		}
		return collapsiblePanel1;
	}

	private CollapsiblePanel getCollapsiblePanel3() {
		if(collapsiblePanel3 == null) {
			collapsiblePanel3 = new CollapsiblePanel();
			collapsiblePanel3.setBounds(6, 303, 189, 84);
		}
		return collapsiblePanel3;
	}

	public Knowledge getKnowledgeSelectedGraph() {
		return knowledgeSelectedGraph;
	}

	public void setKnowledgeSelectedGraph(Knowledge knowledgeSelectedGraph) {
		this.knowledgeSelectedGraph = knowledgeSelectedGraph;
		if (this.knowledgeSelectedGraph != null) {
			activateToolbarButtons(true);
		}
		else {
			activateToolbarButtons(false);
		}
	}

	private void activateToolbarButtons(boolean activate) {
		// Enable delete an edit buttons in toolbar
		parent.enableToolbarButton("AddKnowledge", activate);
		parent.enableToolbarButton("ModifyKnowledge", activate);
		parent.enableToolbarButton("DeleteKnowledge", activate);
		
	}

	public File showAttachFileDialog() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".*");
		JFileChooser fileChooser = new JFileChooser();       
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(this);
		File file = null;
		if (result == JFileChooser.APPROVE_OPTION)
			file = fileChooser.getSelectedFile();
		
		return file;
	}

}
