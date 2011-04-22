package presentation.panelsManageKnowledge;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.jdesktop.application.Application;

import bussiness.control.ClientController;
import bussiness.control.OperationsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import exceptions.NoProposalsException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private ArrayList<Proposal> proposals;

	public JPManageProposal() {
		super();
		initGUI();
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
					}
					{
						btnSaveProposal = new JButton();
						panelAddProposal.add(btnSaveProposal, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveProposal.setName("btnSaveProposal");
						btnSaveProposal.setBounds(297, 228, 68, 24);
						btnSaveProposal.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnSaveProposalActionPerformed(evt);
							}
						});
					}
					{
						cbTopics = new JComboBox();
						panelAddProposal.add(cbTopics, new AnchorConstraint(32,726,112,285,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						cbTopics.setBounds(134, 9, 191, 23);
						setItemsComboTopics();
					}
					{
						lblTopic = new JLabel();
						panelAddProposal.add(lblTopic, new AnchorConstraint(43,243,102,28,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						lblTopic.setPreferredSize(new java.awt.Dimension(93, 17));
						lblTopic.setName("lblTopic");
						lblTopic.setBounds(12, 12, 93, 17);
					}
					{
						proposalInfoAdd = new JPProposalInfo();
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
						proposalInfoModify = new JPProposalInfo();
						panelModifyProposal.add(proposalInfoModify);
						proposalInfoModify.setBounds(12, 44, 431, 172);
					}
					{
						btnCancelModify = new JButton();
						panelModifyProposal.add(btnCancelModify);
						btnCancelModify.setBounds(376, 227, 67, 25);
						btnCancelModify.setName("btnCancelModify");
					}
					{
						btnSaveModify = new JButton();
						panelModifyProposal.add(btnSaveModify);
						btnSaveModify.setBounds(297, 228, 68, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnSaveModifyActionPerformed(evt);
							}
						});
					}
					{
						ComboBoxModel cbAnswersModel = 
							new DefaultComboBoxModel(
									new String[] { "Item One", "Item Two" });
						cbProposals = new JComboBox();
						panelModifyProposal.add(cbProposals);
						cbProposals.setModel(cbAnswersModel);
						cbProposals.setBounds(307, 9, 136, 23);
					}
					{
						lblProposals = new JLabel();
						panelModifyProposal.add(lblProposals);
						lblProposals.setName("lblProposals");
						lblProposals.setBounds(258, 12, 49, 17);
					}
					{
						ComboBoxModel cbProposalsModel = 
							new DefaultComboBoxModel(
									new String[] { "Item One", "Item Two" });
						cbTopicsModify = new JComboBox();
						panelModifyProposal.add(cbTopicsModify);
						cbTopicsModify.setModel(cbProposalsModel);
						cbTopicsModify.setBounds(110, 9, 136, 23);
					}
					{
						lblTopicModify = new JLabel();
						panelModifyProposal.add(lblTopicModify);
						lblTopicModify.setName("lblTopicModify");
						lblTopicModify.setBounds(12, 12, 109, 16);
					}
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
			// Hide tabs not available 
			List<String> operationsId = OperationsUtilities.getAllOperationsId(ClientController.getInstance().getAvailableOperations());
			if (!operationsId.contains("Add"))
				tabPanelProposal.remove(panelAddProposal);
			if (!operationsId.contains("Modify"))
				tabPanelProposal.remove(panelModifyProposal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setItemsComboTopics() {
		// Get topics
		try {
			topics = ClientController.getInstance().getTopicsWrapper().getTopics();
			if (topics.size() == 0)
				;
			for (int i=0; i<topics.size(); i++)
				cbTopics.insertItemAt(topics.get(i).getTitle(), i); 
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
		}		
	}
	
	private void setItemsComboProposals() {
		// Get proposals
		try {
			proposals = ClientController.getInstance().getProposals();
			if (proposals.size() == 0)
				;
			for (int i=0; i<proposals.size(); i++)
				cbTopicsModify.insertItemAt(proposals.get(i).getTitle(), i); 
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
		}	
		catch (NoProposalsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void btnSaveProposalActionPerformed(ActionEvent evt) {
		Proposal pro = new Proposal(proposalInfoAdd.getProposalTitle(), proposalInfoAdd.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoAdd.getProposalCategory()));
		try {
			ClientController.getInstance().addProposal(pro, topics.get(cbTopics.getSelectedIndex()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void btnSaveModifyActionPerformed(ActionEvent evt) {
		Proposal newPro = new Proposal(proposalInfoModify.getProposalTitle(), proposalInfoModify.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoModify.getProposalCategory()));
		try {
			// TODO: se necesita el topic, porque una propuesta no almacena su padre topic
			
			ClientController.getInstance().modifyProposal(newPro, proposals.get(cbTopicsModify.getSelectedIndex()), topics.get(cbTopics.getSelectedIndex()));
//			ClientController.getInstance().deleteProposal(proposals.get(cbTopicsModify.getSelectedIndex()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
