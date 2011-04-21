package presentation;
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
public class JPManageTopic extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1064240066762280491L;
	private JTabbedPane tabPanelTopic;
	private JPanel panelModifyTopic;
	private JPTopicInfo panelTopicInfoAdd;
	private JButton btnSaveModify;
	private JButton btnCancelModify;
	private JButton btnCancelAdd;
	private JButton btnSaveTopic;
	private JPTopicInfo panelTopicInfoModify;
	private JPanel panelAddTopic;
	private ArrayList<Topic> topics;
	private ArrayList<Proposal> proposals;

	public JPManageTopic() {
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
				tabPanelTopic = new JTabbedPane();
				this.add(tabPanelTopic, new AnchorConstraint(1, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tabPanelTopic.setPreferredSize(new java.awt.Dimension(438, 317));
				{
					panelAddTopic = new JPanel();
					tabPanelTopic.addTab("Add Topic", null, panelAddTopic, null);
					panelAddTopic.setLayout(null);
					panelAddTopic.setPreferredSize(new java.awt.Dimension(439, 317));
					panelAddTopic.setSize(439, 317);
					panelAddTopic.setName("panelAddProposals");
					{
						btnCancelAdd = new JButton();
						panelAddTopic.add(btnCancelAdd, new AnchorConstraint(835, 961, 932, 807, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnCancelAdd.setName("btnCancelAdd");
						btnCancelAdd.setBounds(376, 217, 67, 25);
					}
					{
						btnSaveTopic = new JButton();
						panelAddTopic.add(btnSaveTopic, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveTopic.setName("btnSaveTopic");
						btnSaveTopic.setBounds(297, 217, 68, 24);
						btnSaveTopic.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnSaveTopicActionPerformed(evt);
							}
						});
					}
					{
						panelTopicInfoAdd = new JPTopicInfo();
						panelAddTopic.add(panelTopicInfoAdd);
						panelTopicInfoAdd.setBounds(12, 12, 431, 174);
					}
				}
				{
					panelModifyTopic = new JPanel();
					tabPanelTopic.addTab("Modify Topic", null, panelModifyTopic, null);
					panelModifyTopic.setName("panelModifyTopic");
					panelModifyTopic.setLayout(null);
					{
						btnCancelModify = new JButton();
						panelModifyTopic.add(btnCancelModify);
						btnCancelModify.setBounds(376, 217, 67, 25);
						btnCancelModify.setName("btnCancelModify");
					}
					{
						btnSaveModify = new JButton();
						panelModifyTopic.add(btnSaveModify);
						btnSaveModify.setBounds(297, 217, 68, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnSaveModifyActionPerformed(evt);
							}
						});
					}
					{
						panelTopicInfoModify = new JPTopicInfo();
						panelModifyTopic.add(panelTopicInfoModify);
						panelTopicInfoModify.setBounds(12, 12, 431, 174);
					}
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
			// Hide tabs not available 
			List<String> operationsId = OperationsUtilities.getAllOperationsId(ClientController.getInstance().getAvailableOperations());
			if (!operationsId.contains("Add"))
				tabPanelTopic.remove(panelAddTopic);
			if (!operationsId.contains("Modify"))
				tabPanelTopic.remove(panelModifyTopic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	private void btnSaveTopicActionPerformed(ActionEvent evt) {
//		Proposal pro = new Proposal(proposalInfoAdd.getProposalTitle(), proposalInfoAdd.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoAdd.getProposalCategory()));
//		try {
//			ClientController.getInstance().addProposal(pro, topics.get(cbTopics.getSelectedIndex()));
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void btnSaveModifyActionPerformed(ActionEvent evt) {
//		Proposal newPro = new Proposal(proposalInfoModify.getProposalTitle(), proposalInfoModify.getProposalDescription(), new Date(), Categories.valueOf(proposalInfoModify.getProposalCategory()));
//		try {
//			// TODO: se necesita el topic, porque una propuesta no almacena su padre topic
//			
//			ClientController.getInstance().modifyProposal(newPro, proposals.get(cbTopicsModify.getSelectedIndex()), topics.get(cbTopics.getSelectedIndex()));
////			ClientController.getInstance().deleteProposal(proposals.get(cbTopicsModify.getSelectedIndex()));
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
