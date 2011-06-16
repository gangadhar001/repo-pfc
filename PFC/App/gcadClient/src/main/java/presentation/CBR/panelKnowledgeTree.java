package presentation.CBR;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import model.business.knowledge.TopicWrapper;
import org.jdesktop.application.Application;
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
public class panelKnowledgeTree extends javax.swing.JPanel {

	private JScrollPane jScrollPane1;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private Component txtAreaKnowledge;
	private JLabel lblInfo;
	private JScrollPane jScrollPane2;
	private JToolBar toolbar;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public panelKnowledgeTree() {
		super();
		initGUI();
		toolbar.setVisible(false);
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(223, 334));
			this.setLayout(null);
			this.setSize(227, 480);
			this.setBorder(BorderFactory.createTitledBorder(""));
				{
					jScrollPane1 = new JScrollPane();
					this.add(jScrollPane1);
					jScrollPane1.setBounds(4, 367, 218, 109);
					{
						txtAreaKnowledge = new JTextArea();
						jScrollPane1.setViewportView(txtAreaKnowledge);
						txtAreaKnowledge.setBounds(0, 368, 233, 112);
						txtAreaKnowledge.setPreferredSize(new java.awt.Dimension(213, 102));
					}
				}
				{
					jScrollPane2 = new JScrollPane();
					this.add(jScrollPane2);
					jScrollPane2.setBounds(5, 17, 217, 322);
					tree = new JTree(treeModel);
					jScrollPane2.setViewportView(tree);
					tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
					tree.setCellRenderer(new ImageKnowledgeTreeCellRenderer());
					tree.setName("tree");
				}
				{
					lblInfo = new JLabel();
					this.add(lblInfo);
					lblInfo.setBounds(5, 345, 114, 16);
					lblInfo.setName("lblInfo");
				}
				{
					toolbar = new JToolBar();
					this.add(toolbar);
					toolbar.setBounds(5, 17, 217, 26);
					toolbar.setSize(217, 30);
				}

			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method used to show knowledge tree
	public void showTree(TopicWrapper topicWrapper) {	
		this.removeAll();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Knowledge");
		TreeContentProvider.setContentRootNode(root, topicWrapper);
		treeModel = new DefaultTreeModel(root);
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
		// TODO: para perder la seleccion. no aplicable en esta vista
//		tree.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent evt) {
//				treeMouseClicked(evt);
//			}
//		});

		this.revalidate();
		this.repaint();
	}

	// Enable toolbar in order to modify the tree
	public void setEditable() {
		jScrollPane2.setBounds(jScrollPane2.getBounds().x, (int)(jScrollPane2.getBounds().y + toolbar.getSize().getHeight()), jScrollPane2.getBounds().width, (int)(jScrollPane2.getBounds().height - toolbar.getSize().getHeight()));
		toolbar.setVisible(true);
		// TODO: añadir iconos de add, edit, delete
		
	}
	
//	private void treeMouseClicked(MouseEvent evt) {
//		int row = tree.getRowForLocation(evt.getX(), evt.getY());
//		// Click on empty surface. Clear selection from tree and graph
//		if (row == -1) {
//			clearSelectionTree();
//			clearSelectionGraph();
//		}
//	}

}
