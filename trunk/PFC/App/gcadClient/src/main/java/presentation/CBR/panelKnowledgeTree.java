package presentation.CBR;

import internationalization.ApplicationInternationalization;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import model.business.knowledge.Knowledge;
import model.business.knowledge.KnowledgeStatus;
import model.business.knowledge.TopicWrapper;

import org.jdesktop.application.Application;

import presentation.dataVisualization.TreeContentProvider;
import presentation.utils.ImageKnowledgeTreeCellRenderer;
import presentation.utils.TextPaneUtilities;


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

	/**
	 * 
	 */
	private static final long serialVersionUID = 7404365058926776576L;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private JScrollPane scrollTree;
	private JComboBox cbFilter;
	private JLabel lblFilter;
	private JLabel lblKnowledgeInformation;
	private JScrollPane scrollText;
	private JTextPane txtAreaKnowledge;
	private TopicWrapper wrapper;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public panelKnowledgeTree() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(286, 550));
			this.setLayout(null);
			this.setSize(282, 550);
			this.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("decissionsPanel")));
			{
				scrollText = new JScrollPane();
				this.add(scrollText);
				scrollText.setBounds(12, 381, 263, 158);
				{
					txtAreaKnowledge = new JTextPane();
					scrollText.setViewportView(txtAreaKnowledge);
					txtAreaKnowledge.setBounds(10, 336, 265, 162);
					txtAreaKnowledge.setName("txtAreaKnowledge");
					txtAreaKnowledge.setAutoscrolls(true);
					txtAreaKnowledge.setEditable(false);
					txtAreaKnowledge.setPreferredSize(new java.awt.Dimension(262, 153));
				}
			}
				{
					scrollTree = new JScrollPane();
					scrollTree.setBounds(10, 61, 265, 284);
				}
				
				{
					lblKnowledgeInformation = new JLabel();
					lblKnowledgeInformation.setText(ApplicationInternationalization.getString("knowInfo"));
					this.add(lblKnowledgeInformation);
					lblKnowledgeInformation.setBounds(12, 357, 267, 18);
				}
				{
					lblFilter = new JLabel();
					this.add(lblFilter);
					lblFilter.setBounds(10, 26, 92, 24);
					lblFilter.setText(ApplicationInternationalization.getString("lblFilterName"));
				}
				{
					ComboBoxModel cbFilterModel = 
						new DefaultComboBoxModel(
								new String[] { ApplicationInternationalization.getString("FilterAll"),
										ApplicationInternationalization.getString("FilterAccepted"),
										ApplicationInternationalization.getString("FilterRejected"),
										ApplicationInternationalization.getString("FilterOpen"),
								});
					cbFilter = new JComboBox();
					cbFilter.setSelectedIndex(0);
					this.add(cbFilter);
					cbFilter.setModel(cbFilterModel);
					cbFilter.setBounds(114, 26, 161, 23);
					cbFilter.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							cbFilterActionPerformed();
						}
					});
				}

			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
		}
	}
	
	// Method used to show knowledge tree
	public void showTree(TopicWrapper topicWrapper, KnowledgeStatus filter) {	
		this.remove(scrollTree);
		this.wrapper = topicWrapper;
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Knowledge");
		if (filter.equals(KnowledgeStatus.All))
			TreeContentProvider.setContentRootNode(root, topicWrapper);
		else 
			TreeContentProvider.setContentRootNodeFilter(root, topicWrapper, filter);
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.setBorder(new EmptyBorder(5, 10, 5, 5));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new ImageKnowledgeTreeCellRenderer());
		tree.addTreeSelectionListener(new TreeSelectionListener() {				
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// Get selected element in the tree
				Object val = ((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject();
				if (!(val instanceof TopicWrapper) && (val instanceof Knowledge)) {
					showKnowledgeInfo((Knowledge) val);
				}
			}
		});
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				treeMouseClicked(evt);
			}
		});	
		
		scrollTree.setViewportView(tree);
		this.add(scrollTree);	

		txtAreaKnowledge.setText("");
		
		validate();
		repaint();
	}
	
	protected void showKnowledgeInfo(Knowledge val) {
		txtAreaKnowledge.setText("");
		
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		// Create text and styles for TxetPane
		String[] initString = {
				ApplicationInternationalization.getString("nameKnowledge")+ ": ", 
				val.getTitle(),	
				"\n",
				ApplicationInternationalization.getString("descripKnowledge") + ": ",
				val.getDescription(),
				"\n",
				ApplicationInternationalization.getString("dateKnowledge") + ": ",
				format.format(val.getDate()),
				"\n",
				ApplicationInternationalization.getString("statusKnowledge") + ": ",
				val.getStatus().name(),
				"\n",
				ApplicationInternationalization.getString("authorKnowledge") + ": ",
				"\n",
				"    " + ApplicationInternationalization.getString("nameUserKnowledge") + ": ",
				val.getUser().getName(),
				"\n",
				"    " + ApplicationInternationalization.getString("roleUserKnowledge") + ": ",
				ApplicationInternationalization.getString(val.getUser().getRole().name()),
				"\n",
				"    " + ApplicationInternationalization.getString("countryUserKnowledge") + ": ",
				val.getUser().getCompany().getAddress().getCountry(),
				"\n",
				ApplicationInternationalization.getString("countryUserKnowledge") + ": ",
				val.getUser().getCompany().getName() + "(" + val.getUser().getCompany().getAddress().getCountry() + ")"
				
		};
		
		String[] initStyles = { 
				"bold", "regular", "regular",
				"bold", "regular", "regular",
				"bold", "regular", "regular",
				"bold", "regular", "regular",
				"bold", "regular", 
				"bold", "regular", "regular",
				"bold", "regular", "regular", 
				"bold", "regular", "regular", 
				"bold", "regular"
		};
		
		TextPaneUtilities.setStyledText(txtAreaKnowledge, initString, initStyles);
	}
	 		
	private void treeMouseClicked(MouseEvent evt) {
		int row = tree.getRowForLocation(evt.getX(), evt.getY());
		// Click on empty surface. Clear selection from tree
		if (row == -1) {
			tree.clearSelection();
			txtAreaKnowledge.setText("");
		}		
	}
	
	private void cbFilterActionPerformed() {
		int selected = cbFilter.getSelectedIndex();
		if (selected != -1) {
			if (selected == 0) 
				showTree(wrapper, KnowledgeStatus.All);
			else if (selected == 1) 
				showTree(wrapper, KnowledgeStatus.Accepted);
			else if (selected == 2) 
				showTree(wrapper, KnowledgeStatus.Rejected);
			else 
				showTree(wrapper, KnowledgeStatus.Open);
		}
	}

}
