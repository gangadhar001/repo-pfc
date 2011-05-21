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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

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

	public JPManageProposal(JDialog parent) {
		super();
		this.parentD = parent;
		data = null;
		initGUI();
	}
	
	public JPManageProposal(JDialog parent, Object data, String operationToDo) {
		super();
		this.data = data;
		this.operationToDo = operationToDo;
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(460, 288));
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setSize(460, 288);
			{
				tabPanelProposal = new JTabbedPane();
				this.add(tabPanelProposal, new AnchorConstraint(1, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tabPanelProposal.setPreferredSize(new java.awt.Dimension(438, 317));
				{
					panelAddProposal = new JPanel();
					tabPanelProposal.addTab("Add Proposal", null, panelAddProposal, null);
					panelAddProposal.setLayout(null);
					panelAddProposal.setPreferredSize(new java.awt.Dimension(439, 317));
					panelAddProposal.setSize(439, 317);
					panelAddProposal.setName("panelAddProposals");
					{
						btnCancelAdd = new JButton();
						panelAddProposal.add(btnCancelAdd, new AnchorConstraint(835, 961, 932, 807, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnCancelAdd.setName("btnCancelAdd");
						btnCancelAdd.setBounds(376, 227, 67, 25);
						btnCancelAdd.setAction(getAppActionMap().get("Cancel"));
						btnCancelAdd.setText(ApplicationInternationalization.getString("CancelButton"));
						
					}
					{
						btnSaveProposal = new JButton();
						panelAddProposal.add(btnSaveProposal, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveProposal.setName("btnSaveProposal");
						btnSaveProposal.setBounds(297, 228, 68, 24);
						btnSaveProposal.setAction(getAppActionMap().get("Save"));
						btnSaveProposal.setText(ApplicationInternationalization.getString("btnSave"));
					}
					{
						cbTopics = new JComboBox();
						panelAddProposal.add(cbTopics, new AnchorConstraint(32,726,112,285,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						cbTopics.setBounds(61, 9, 195, 22);
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
						proposalInfoAdd.setPreferredSize(new java.awt.Dimension(431, 172));
					}
				}
				{
					panelModifyProposal = new JPanel();
					tabPanelProposal.addTab("Modify Proposal", null, panelModifyProposal, null);
					panelModifyProposal.setName("panelModifyProposal");
					panelModifyProposal.setLayout(null);
					{
						proposalInfoModify = new JPProposalInfo(parentD);
						panelModifyProposal.add(proposalInfoModify);
						proposalInfoModify.setBounds(12, 44, 431, 172);
					}
					{
						btnCancelModify = new JButton();
						panelModifyProposal.add(btnCancelModify);
						btnCancelModify.setBounds(376, 227, 67, 25);
						btnCancelModify.setName("btnCancelModify");
						btnCancelModify.setAction(getAppActionMap().get("Cancel"));
						btnCancelModify.setText(ApplicationInternationalization.getString("CancelButton"));
					}
					{
						btnSaveModify = new JButton();
						panelModifyProposal.add(btnSaveModify);
						btnSaveModify.setBounds(297, 228, 68, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.setAction(getAppActionMap().get("Modify"));
						btnSaveModify.setText(ApplicationInternationalization.getString("btnSave"));
					}
					{
						
						cbProposals = new JComboBox();
						panelModifyProposal.add(cbProposals);
						cbProposals.setBounds(307, 9, 136, 23);
						cbProposals.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cbProposalsActionPerformed(evt);
							}
						});

					}
					{
						lblProposals = new JLabel();
						panelModifyProposal.add(lblProposals);
						lblProposals.setName("lblProposals");
						lblProposals.setBounds(241, 12, 67, 17);
						lblProposals.setText(ApplicationInternationalization.getString("lblProposal"));
					}
					{
						cbTopicsModify = new JComboBox();
						panelModifyProposal.add(cbTopicsModify);
						cbTopicsModify.setBounds(59, 9, 140, 22);
						cbTopicsModify.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cbTopicsModifyActionPerformed(evt);
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
			if (!operationsId.contains("Add"))
				tabPanelProposal.remove(panelAddProposal);
			if (!operationsId.contains("Modify"))
				tabPanelProposal.remove(panelModifyProposal);
			
			// If this panel is invoked by knowledge view, with an item already selected, fill the data
			if (data != null)
				fillData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cbProposalsActionPerformed(ActionEvent evt) {
		proposalInfoModify.fillData(proposals[cbProposals.getSelectedIndex()]);
	}
	
	private void fillData() {
		// Fill fields with the received proposal
		if (operationToDo.equals("Modify")) {
			Proposal p = (Proposal)data;
			proposalInfoModify.fillData(p);
			try {
				Topic t = ClientController.getInstance().findParentProposal(p);
				cbTopicsModify.setSelectedItem(t.getTitle());
				cbTopicsModify.setEnabled(false);
				
				cbProposals.setSelectedItem(p.getTitle());
				cbProposals.setEnabled(false);
			
			} catch (NonPermissionRole e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
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
	}

	private void setItemsComboTopics() {
		// Get topics
		try {
			topics = ClientController.getInstance().getTopicsWrapper().getTopics();
			if (topics.size() == 0)
				;
			for (int i=0; i<topics.size(); i++) {
				cbTopics.insertItemAt(topics.get(i).getTitle(), i);
				cbTopicsModify.insertItemAt(topics.get(i).getTitle(), i);
			}
	
		} catch (NonPermissionRole e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
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
	
	private void setItemsComboProposals() {
		if (proposals != null) {
			for (int i=0; i<proposals.length; i++)
				cbProposals.insertItemAt(proposals[i].getTitle(), i); 
		}
	
	}
	
	// When select a topic, fill the "Proposals" combobox
	private void cbTopicsModifyActionPerformed(ActionEvent evt) {
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
			ClientController.getInstance().addProposal(newPro, topics.get(cbTopics.getSelectedIndex()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
	
	@Action
	public void Modify() {
		Proposal newPro = new Proposal(proposalInfoModify.getProposalTitle(), proposalInfoModify.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoModify.getProposalCategory()));
		try {
			// Modify the old Proposal
			ClientController.getInstance().modifyProposal(newPro, proposals[cbProposals.getSelectedIndex()], topics.get(cbTopicsModify.getSelectedIndex()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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

}
