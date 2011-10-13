package presentation.panelsManageKnowledge;

import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.business.knowledge.Categories;
import model.business.knowledge.Operations;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.JFMain;

import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

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
public class JPManageProposal extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1064240066762280491L;
	private JTabbedPane tabPanelProposal;
	private JPanel panelModifyProposal;
	private JLabel lblTopicModify;
	private JComboBox cbTopicsModify;
	private JLabel lblProposals;
	private JComboBox cbProposals;
	private JPProposalInfo proposalInfoAdd;
	private JButton btnSaveModify;
	private JButton btnCancelModify;
	private JPProposalInfo proposalInfoModify;
	private JButton btnCancelAdd;
	private JButton btnSaveProposal;
	private JComboBox cbTopics;
	private JLabel lblTopic;
	private JPanel panelAddProposal;
	private ArrayList<Topic> topics;
	private Proposal[] proposals;
	
	private Object data;
	private String operationToDo;
	private JDialog parentD;
	private JFMain mainFrame;

	public JPManageProposal(JFMain mainFrame, JDialog parent) {
		super();
		this.parentD = parent;
		data = null;
		this.mainFrame = mainFrame;
		initGUI();
	}
	
	public JPManageProposal(JFMain mainFrame, JDialog parent, Object data, String operationToDo) {
		super();
		this.data = data;
		this.operationToDo = operationToDo;
		this.parentD = parent;
		this.mainFrame = mainFrame;
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(560, 288));
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setSize(560, 288);
			this.setMinimumSize(new java.awt.Dimension(560, 288));
			{
				tabPanelProposal = new JTabbedPane();
				this.add(tabPanelProposal, new AnchorConstraint(1, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tabPanelProposal.setPreferredSize(new java.awt.Dimension(438, 317));
				{
					panelAddProposal = new JPanel();
					tabPanelProposal.addTab(ApplicationInternationalization.getString("tabManageProposal_Add"), null, panelAddProposal, null);
					panelAddProposal.setLayout(null);
					panelAddProposal.setPreferredSize(new java.awt.Dimension(439, 317));
					panelAddProposal.setSize(439, 317);
					panelAddProposal.setName("panelAddProposals");
					{
						btnCancelAdd = new JButton();
						panelAddProposal.add(btnCancelAdd, new AnchorConstraint(835, 961, 932, 807, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnCancelAdd.setName("btnCancelAdd");
						btnCancelAdd.setBounds(453, 228, 91, 24);
						btnCancelAdd.setAction(getAppActionMap().get("Cancel"));
						btnCancelAdd.setText(ApplicationInternationalization.getString("CancelButton"));

					}
					{
						btnSaveProposal = new JButton();
						panelAddProposal.add(btnSaveProposal, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveProposal.setName("btnSaveProposal");
						btnSaveProposal.setBounds(351, 228, 91, 24);
						btnSaveProposal.setAction(getAppActionMap().get("Save"));
						btnSaveProposal.setText(ApplicationInternationalization.getString("btnSave"));
					}
					{
						cbTopics = new JComboBox();
						panelAddProposal.add(cbTopics, new AnchorConstraint(32,726,112,285,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						cbTopics.setBounds(61, 9, 195, 22);
						cbTopics.setSize(167, 23);
					}
					{
						lblTopic = new JLabel();
						panelAddProposal.add(lblTopic, new AnchorConstraint(43,243,102,28,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						lblTopic.setName("lblTopic");
						lblTopic.setBounds(12, 12, 49, 17);
						lblTopic.setText(ApplicationInternationalization.getString("lblTopic"));
					}
					{
						proposalInfoAdd = new JPProposalInfo(parentD);
						panelAddProposal.add(proposalInfoAdd);
						proposalInfoAdd.setBounds(12, 44, 431, 172);
						proposalInfoAdd.setPreferredSize(new java.awt.Dimension(531, 172));
						proposalInfoAdd.setMinimumSize(new java.awt.Dimension(531, 172));
						proposalInfoAdd.setSize(531, 172);
					}
				}
				{
					panelModifyProposal = new JPanel();
					tabPanelProposal.addTab(ApplicationInternationalization.getString("tabManageProposal_Modify"), null, panelModifyProposal, null);
					panelModifyProposal.setName("panelModifyProposal");
					panelModifyProposal.setLayout(null);
					{
						proposalInfoModify = new JPProposalInfo(parentD);
						panelModifyProposal.add(proposalInfoModify);
						proposalInfoModify.setBounds(12, 44, 431, 172);
						proposalInfoModify.setSize(531, 172);
						proposalInfoModify.setPreferredSize(new java.awt.Dimension(531, 172));
					}
					{
						btnCancelModify = new JButton();
						panelModifyProposal.add(btnCancelModify);
						btnCancelModify.setBounds(453, 228, 91, 24);
						btnCancelModify.setName("btnCancelModify");
						btnCancelModify.setAction(getAppActionMap().get("Cancel"));
						btnCancelModify.setText(ApplicationInternationalization.getString("CancelButton"));
					}
					{
						btnSaveModify = new JButton();
						panelModifyProposal.add(btnSaveModify);
						btnSaveModify.setBounds(351, 228, 91, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.setAction(getAppActionMap().get("Modify"));
						btnSaveModify.setText(ApplicationInternationalization.getString("btnSave"));
					}
					{
						
						cbProposals = new JComboBox();
						panelModifyProposal.add(cbProposals);
						cbProposals.setBounds(376, 9, 167, 23);
						cbProposals.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cbProposalsActionPerformed();
							}
						});

					}
					{
						lblProposals = new JLabel();
						panelModifyProposal.add(lblProposals);
						lblProposals.setName("lblProposals");
						lblProposals.setBounds(309, 12, 67, 17);
						lblProposals.setText(ApplicationInternationalization.getString("lblProposal"));
					}
					{
						cbTopicsModify = new JComboBox();
						panelModifyProposal.add(cbTopicsModify);
						cbTopicsModify.setBounds(59, 9, 140, 22);
						cbTopicsModify.setSize(167, 23);
						cbTopicsModify.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cbTopicsModifyActionPerformed();
							}
						});
					}
					{
						lblTopicModify = new JLabel();
						panelModifyProposal.add(lblTopicModify);
						lblTopicModify.setName("lblTopicModify");
						lblTopicModify.setBounds(12, 12, 47, 16);
						lblTopicModify.setText(ApplicationInternationalization.getString("lblTopic"));
					}
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
			setItemsComboTopics();
			setItemsComboProposals();
			
			// Hide tabs not available 
			List<String> operationsId = OperationsUtilities.getAllOperations(ClientController.getInstance().getAvailableOperations());
			if (!operationsId.contains(Operations.Add.name()))
				tabPanelProposal.remove(panelAddProposal);
			if (!operationsId.contains(Operations.Modify.name()))
				tabPanelProposal.remove(panelModifyProposal);
			
			// If this panel is invoked by knowledge view, with an item already selected, fill the data
			if (data != null) {
				fillData();
				// Disable tabs not used for that operation
				int indexModify = getIndexTab(ApplicationInternationalization.getString("tabManageAnswer_Modify"));
				int indexAdd = getIndexTab(ApplicationInternationalization.getString("tabManageAnswer_Add"));
				if (operationToDo.equals(Operations.Add.name())) {
					if (indexModify != -1)
						tabPanelProposal.getTabComponentAt(indexModify).setEnabled(false);
					if (indexAdd != -1)
						tabPanelProposal.setSelectedIndex(indexAdd);
				}
				else if (operationToDo.equals(Operations.Modify.name())) {
					if (indexAdd != -1)
						tabPanelProposal.getTabComponentAt(indexAdd).setEnabled(false);
					if (indexModify != -1)
						tabPanelProposal.setSelectedIndex(indexModify);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cbProposalsActionPerformed() {
		proposalInfoModify.fillData(proposals[cbProposals.getSelectedIndex()]);
	}
	
	private void fillData() {
		// Fill fields with the received proposal
		if (operationToDo.equals(Operations.Modify.name())) {
			Proposal p = (Proposal)data;
			proposalInfoModify.fillData(p);
			try {
				Topic t = ClientController.getInstance().findParentProposal(p);
				cbTopicsModify.setSelectedItem(t.getTitle());
				cbTopicsModify.setEnabled(false);
				
				cbProposals.setSelectedItem(p.getTitle());
				cbProposals.setEnabled(false);
			
			} catch (NonPermissionRoleException e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}		
	}

	private void setItemsComboTopics() {
		// Get topics
		try {
			topics = ClientController.getInstance().getTopicsWrapper().getTopics();
			if (topics.size() == 0)
				JOptionPane.showMessageDialog(parentD, ApplicationInternationalization.getString("panelManageAnswer_NotTopics"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			for (int i=0; i<topics.size(); i++) {
				cbTopics.insertItemAt(topics.get(i).getTitle(), i);
				cbTopicsModify.insertItemAt(topics.get(i).getTitle(), i);
			}
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void setItemsComboProposals() {
		if (proposals != null) {
			for (int i=0; i<proposals.length; i++)
				cbProposals.insertItemAt(proposals[i].getTitle(), i); 
		}
	
	}
	
	// When select a topic, fill the "Proposals" combobox
	private void cbTopicsModifyActionPerformed() {
		if (cbTopicsModify.getSelectedIndex() != -1) {	
			Topic t = topics.get(cbTopicsModify.getSelectedIndex());
			Object[] aux =  t.getProposals().toArray();
			proposals = new Proposal[aux.length];
			for (int i = 0; i<aux.length; i++)
				proposals[i] = (Proposal) aux[i];
			setItemsComboProposals();			
		}
	}
	
	/*** Actions for buttons ***/
	@Action
	public void Cancel () {
		parentD.dispose();
	}	
	
	@Action
	public void Save() {
		Proposal newPro = new Proposal(proposalInfoAdd.getProposalTitle(), proposalInfoAdd.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoAdd.getProposalCategory()));
		try {
			// Create and insert new Proposal
			Proposal newProposalAdded = ClientController.getInstance().addProposal(newPro, topics.get(cbTopics.getSelectedIndex()));
			// Notify to main frame the new knowledge
			mainFrame.notifyKnowledgeAdded(newProposalAdded, topics.get(cbTopics.getSelectedIndex()));
			parentD.dispose();
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Action
	public void Modify() {
		Proposal oldPro = proposals[cbProposals.getSelectedIndex()];
		Proposal newPro = new Proposal(proposalInfoModify.getProposalTitle(), proposalInfoModify.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoModify.getProposalCategory()));
		newPro.setId(oldPro.getId());
		try {
			// Modify the old Proposal
			Proposal newProposalModified = ClientController.getInstance().modifyProposal(newPro, proposals[cbProposals.getSelectedIndex()], topics.get(cbTopicsModify.getSelectedIndex()));
			// Notify to main frame the new knowledge
			mainFrame.notifyKnowledgeEdited(newProposalModified, oldPro);
			parentD.dispose();
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private int getIndexTab(String title) {
    	int result = -1;
    	for(int i=0; i<tabPanelProposal.getTabCount() && result==-1; i++) {
    		if (tabPanelProposal.getTitleAt(i).equals(title))
    			result=i;
    	}
    	return result;
	}

}
