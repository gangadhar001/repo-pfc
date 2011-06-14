package presentation.CBR;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import model.business.knowledge.Knowledge;
import model.business.knowledge.Project;
import model.business.knowledge.TopicWrapper;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import bussiness.control.ClientController;

import com.cloudgarden.layout.AnchorConstraint;

import exceptions.NonPermissionRole;
import exceptions.NotLoggedException;

import presentation.panelProjectInformation;
import presentation.dataVisualization.TreeContentProvider;
import presentation.utils.ImageKnowledgeTreeCellRenderer;

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
public class JDRetrievalCases extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5940879165571381847L;
	private panelProjectInformation currentPanel;
	private JButton btnBackward;
	private JButton btnForward;
	private JLabel lblNumberCases;

	/**
	* Auto-generated main method to display this JDialog
	*/		
	
	private List<Project> cases;
	private JScrollPane jScrollPane1;
	private JPanel panelTree;
	private JButton btnOK;
	private JButton btnCancel;
	private JTree tree;
	private int currentProject;
	private DefaultTreeModel treeModel;
	
	public JDRetrievalCases(JFrame frame, List<Project> cases) {
		super(frame);
		this.cases = cases;
		currentProject = 1;
		initGUI();
		currentPanel.showData(cases.get(currentProject-1), false);
		// Show tree of knowledge for this project
		try {
			showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRole e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	// Method used to show knowledge tree
	private void showTree(TopicWrapper topicWrapper) {	
		panelTree.removeAll();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Knowledge");
		TreeContentProvider.setContentRootNode(root, topicWrapper);
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new ImageKnowledgeTreeCellRenderer());
		// TODO: No es editable en esta vista
		// TODO: hacer un panel aparte
//		tree.addTreeSelectionListener(new TreeSelectionListener() {				
//			@Override
//			public void valueChanged(TreeSelectionEvent e) {
//				// Get selected element in the tree
//				Object val = ((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject();
//				if (!(val instanceof TopicWrapper)) {
//					knowledgeSelectedTree = (Knowledge) val;
//				}
//			}
//		});
		jScrollPane1.setViewportView(tree);
		tree.setPreferredSize(new java.awt.Dimension(125, 411));
		// TODO: para perder la seleccion. no aplicable en esta vista
//		tree.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent evt) {
//				treeMouseClicked(evt);
//			}
//		});

		panelTree.add(jScrollPane1);
		
		panelTree.revalidate();
		panelTree.repaint();
	}
	
//	private void treeMouseClicked(MouseEvent evt) {
//		int row = tree.getRowForLocation(evt.getX(), evt.getY());
//		// Click on empty surface. Clear selection from tree and graph
//		if (row == -1) {
//			clearSelectionTree();
//			clearSelectionGraph();
//		}
//	}
	
	private void initGUI() {		
		try {
			{
				getContentPane().setLayout(null);
				{
					currentPanel = new panelProjectInformation();
					getContentPane().add(currentPanel);
					currentPanel.setBounds(12, 12, 272, 398);
				}
				{
					lblNumberCases = new JLabel();
					getContentPane().add(lblNumberCases);
					lblNumberCases.setBounds(117, 422, 79, 16);
					lblNumberCases.setName("lblNumberCases");
					lblNumberCases.setText("Case " + currentProject+"/"+cases.size());
				}
				{
					btnForward = new JButton();
					getContentPane().add(btnForward);
					btnForward.setBounds(196, 419, 45, 23);
					btnForward.setAction(getAppActionMap().get("Forward"));
					btnForward.setName("btnForward");
				}
				{
					btnBackward = new JButton();
					getContentPane().add(btnBackward);
					btnBackward.setBounds(65, 419, 43, 23);
					btnBackward.setAction(getAppActionMap().get("Backward"));
					btnBackward.setName("btnBackward");
					btnBackward.setEnabled(false);
				}
				{
					panelTree = new JPanel();
					getContentPane().add(panelTree);
					panelTree.setBounds(296, 12, 227, 398);
					panelTree.setLayout(null);
					{
						jScrollPane1 = new JScrollPane();
						panelTree.add(jScrollPane1);
						jScrollPane1.setBounds(0, 5, 227, 393);
						{
							tree = new JTree();
							jScrollPane1.setViewportView(tree);
							tree.setBounds(296, 12, 227, 398);
						}
					}
				}
				{
					btnCancel = new JButton();
					getContentPane().add(btnCancel);
					btnCancel.setBounds(437, 468, 82, 25);
					btnCancel.setName("btnCancel");
				}
				{
					btnOK = new JButton();
					getContentPane().add(btnOK);
					btnOK.setBounds(350, 468, 82, 25);
					btnOK.setName("btnOK");
				}
			}
			this.setSize(551, 542);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action
	public void Forward() {
		if (currentProject < cases.size()) {
			currentProject++;
			btnBackward.setEnabled(true);
			currentPanel.showData(cases.get(currentProject-1), false);
			try {
				showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonPermissionRole e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotLoggedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			lblNumberCases.setText("Case " + currentProject+"/"+cases.size());
		}
		if(currentProject == cases.size()) {
			btnForward.setEnabled(false);
		}
	}
	
	@Action
	public void Backward() {
		if (currentProject > 0) {
			currentProject--;
			btnForward.setEnabled(true);
			currentPanel.showData(cases.get(currentProject-1), false);
			try {
				showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonPermissionRole e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotLoggedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			lblNumberCases.setText("Case " + currentProject+"/"+cases.size());
		}
		if(currentProject == 1) {
			btnBackward.setEnabled(false);
		}
	}
}
