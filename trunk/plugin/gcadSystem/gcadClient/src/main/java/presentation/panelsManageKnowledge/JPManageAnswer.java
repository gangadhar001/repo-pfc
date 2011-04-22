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
public class JPManageAnswer extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1064240066762280491L;
	private JTabbedPane tabPanelAnswer;
	private JPanel panelModifyAnswer;
	private JPAnswerInfo panelAnswerInfoModify;
	private JComboBox cbAnswers;
	private JLabel lblAnswerModify;
	private JPAnswerInfo panelAnswerInfoAdd;
	private JButton btnSaveModify;
	private JButton btnCancelModify;
	private JComboBox cbProposals;
	private JLabel lblProposals;
	private JButton btnCancelAdd;
	private JButton btnSaveAnswer;
	private JComboBox cbProposalsAdd;
	private JLabel lblProposal;
	private JPanel panelAddAnswer;
	private ArrayList<Topic> topics;
	private ArrayList<Proposal> proposals;

	public JPManageAnswer() {
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
				tabPanelAnswer = new JTabbedPane();
				this.add(tabPanelAnswer, new AnchorConstraint(1, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tabPanelAnswer.setPreferredSize(new java.awt.Dimension(438, 317));
				{
					panelAddAnswer = new JPanel();
					tabPanelAnswer.addTab("Add Answer", null, panelAddAnswer, null);
					panelAddAnswer.setLayout(null);
					panelAddAnswer.setPreferredSize(new java.awt.Dimension(429, 264));
					panelAddAnswer.setSize(439, 317);
					panelAddAnswer.setName("panelAddProposals");
					{
						btnCancelAdd = new JButton();
						panelAddAnswer.add(btnCancelAdd, new AnchorConstraint(835, 961, 932, 807, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnCancelAdd.setName("btnCancelAdd");
						btnCancelAdd.setBounds(376, 227, 67, 25);
					}
					{
						btnSaveAnswer = new JButton();
						panelAddAnswer.add(btnSaveAnswer, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveAnswer.setName("btnSaveAnswer");
						btnSaveAnswer.setBounds(297, 228, 68, 24);
						btnSaveAnswer.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnSaveAnswerActionPerformed(evt);
							}
						});
					}
					{
						cbProposalsAdd = new JComboBox();
						panelAddAnswer.add(cbProposalsAdd, new AnchorConstraint(32,726,112,285,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						cbProposalsAdd.setBounds(134, 9, 191, 23);
						setItemsComboTopics();
					}
					{
						lblProposal = new JLabel();
						panelAddAnswer.add(lblProposal, new AnchorConstraint(43,243,102,28,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL,AnchorConstraint.ANCHOR_REL));
						lblProposal.setName("lblProposal");
						lblProposal.setBounds(12, 12, 110, 17);
					}
					{
						panelAnswerInfoAdd = new JPAnswerInfo();
						panelAddAnswer.add(panelAnswerInfoAdd);
						panelAnswerInfoAdd.setBounds(12, 44, 431, 172);
					}
				}
				{
					panelModifyAnswer = new JPanel();
					tabPanelAnswer.addTab("Modify Answer", null, panelModifyAnswer, null);
					panelModifyAnswer.setName("panelModifyAnswer");
					panelModifyAnswer.setLayout(null);
					{
						lblProposals = new JLabel();
						panelModifyAnswer.add(lblProposals);
						lblProposals.setBounds(12, 12, 109, 16);
						lblProposals.setName("lblProposals");
					}
					{
						cbProposals = new JComboBox();
						panelModifyAnswer.add(cbProposals);
						cbProposals.setBounds(110, 9, 136, 23);
						setItemsComboProposals();
					}
					{
						btnCancelModify = new JButton();
						panelModifyAnswer.add(btnCancelModify);
						btnCancelModify.setBounds(376, 227, 67, 25);
						btnCancelModify.setName("btnCancelModify");
					}
					{
						btnSaveModify = new JButton();
						panelModifyAnswer.add(btnSaveModify);
						btnSaveModify.setBounds(297, 228, 68, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnSaveModifyActionPerformed(evt);
							}
						});
					}
					{
						lblAnswerModify = new JLabel();
						panelModifyAnswer.add(lblAnswerModify);
						lblAnswerModify.setBounds(258, 12, 49, 17);
						lblAnswerModify.setName("lblAnswerModify");
					}
					{
						ComboBoxModel cbAnswersModel = 
							new DefaultComboBoxModel(
									new String[] { "Item One", "Item Two" });
						cbAnswers = new JComboBox();
						panelModifyAnswer.add(cbAnswers);
						cbAnswers.setModel(cbAnswersModel);
						cbAnswers.setBounds(307, 9, 136, 23);
					}
					{
						panelAnswerInfoModify = new JPAnswerInfo();
						panelModifyAnswer.add(panelAnswerInfoModify);
						panelAnswerInfoModify.setBounds(12, 44, 431, 172);
					}
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
			// Hide tabs not available 
			List<String> operationsId = OperationsUtilities.getAllOperationsId(ClientController.getInstance().getAvailableOperations());
			if (!operationsId.contains("Add"))
				tabPanelAnswer.remove(panelAddAnswer);
			if (!operationsId.contains("Modify"))
				tabPanelAnswer.remove(panelModifyAnswer);
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
				cbProposalsAdd.insertItemAt(topics.get(i).getTitle(), i); 
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
				cbProposals.insertItemAt(proposals.get(i).getTitle(), i); 
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
	
	private void btnSaveAnswerActionPerformed(ActionEvent evt) {
//		Proposal pro = new Proposal(proposalAnswerAdd.getProposalTitle(), proposalAnswerAdd.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoAdd.getProposalCategory()));
//		try {
//			ClientController.getInstance().addProposal(pro, topics.get(cbProposalsAdd.getSelectedIndex()));
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void btnSaveModifyActionPerformed(ActionEvent evt) {
//		Proposal newPro = new Proposal(proposalAnswerModify.getProposalTitle(), proposalAnswerModify.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoModify.getProposalCategory()));
//		try {
//			// TODO: se necesita el topic, porque una propuesta no almacena su padre topic
//			
//			ClientController.getInstance().modifyProposal(newPro, proposals.get(cbProposals.getSelectedIndex()), topics.get(cbProposalsAdd.getSelectedIndex()));
////			ClientController.getInstance().deleteProposal(proposals.get(cbProposals.getSelectedIndex()));
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
