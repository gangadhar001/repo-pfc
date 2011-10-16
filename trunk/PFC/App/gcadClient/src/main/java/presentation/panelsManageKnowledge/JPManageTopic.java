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

import model.business.knowledge.Operations;
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
	private JComboBox cbTopics;
	private JLabel lblTopics;
	private JPTopicInfo panelTopicInfoModify;
	private JPanel panelAddTopic;
	private ArrayList<Topic> topics;
	private JDialog parentD;
	private Object data;
	private String operationToDo;
	private JFMain mainFrame;

	public JPManageTopic(JFMain mainframe, JDialog parent) {
		super();
		this.parentD = parent;
		data = null;
		this.mainFrame = mainframe;
		initGUI();
	}
	
	public JPManageTopic(JFMain mainframe, JDialog parent, Object data, String operationToDo) {
		super();
		this.data = data;
		this.operationToDo = operationToDo;
		this.parentD = parent;
		this.mainFrame = mainframe;
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
			{
				tabPanelTopic = new JTabbedPane();
				this.add(tabPanelTopic, new AnchorConstraint(1, 1001, 1001, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tabPanelTopic.setPreferredSize(new java.awt.Dimension(438, 317));
				{
					panelAddTopic = new JPanel();
					tabPanelTopic.addTab(ApplicationInternationalization.getString("tabManageTopic_Add"), null, panelAddTopic, null);
					panelAddTopic.setLayout(null);
					panelAddTopic.setPreferredSize(new java.awt.Dimension(439, 317));
					panelAddTopic.setSize(439, 317);
					panelAddTopic.setName("panelAddProposals");
					{
						btnCancelAdd = new JButton();
						panelAddTopic.add(btnCancelAdd, new AnchorConstraint(835, 961, 932, 807, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnCancelAdd.setName("btnCancelAdd");
						btnCancelAdd.setBounds(453, 217, 91, 24);
						btnCancelAdd.setAction(getAppActionMap().get("Cancel"));
						btnCancelAdd.setText(ApplicationInternationalization.getString("CancelButton"));						
					}
					{
						btnSaveTopic = new JButton();
						panelAddTopic.add(btnSaveTopic, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveTopic.setName("btnSaveTopic");
						btnSaveTopic.setBounds(351, 217, 91, 24);
						btnSaveTopic.setAction(getAppActionMap().get("Save"));
						btnSaveTopic.setText(ApplicationInternationalization.getString("btnSave"));
					}
					{
						panelTopicInfoAdd = new JPTopicInfo(parentD);
						panelAddTopic.add(panelTopicInfoAdd);
						panelTopicInfoAdd.setBounds(12, 12, 431, 174);
						panelTopicInfoAdd.setSize(531, 172);
						panelTopicInfoAdd.setPreferredSize(new java.awt.Dimension(531, 172));
					}
				}
				{
					panelModifyTopic = new JPanel();
					tabPanelTopic.addTab(ApplicationInternationalization.getString("tabManageTopic_Modify"), null, panelModifyTopic, null);
					panelModifyTopic.setName("panelModifyTopic");
					panelModifyTopic.setLayout(null);
					{
						btnCancelModify = new JButton();
						panelModifyTopic.add(btnCancelModify);
						btnCancelModify.setBounds(453, 217, 91, 24);
						btnCancelModify.setName("btnCancelModify");
						btnCancelModify.setAction(getAppActionMap().get("Cancel"));
						btnCancelModify.setText(ApplicationInternationalization.getString("CancelButton"));
					}
					{
						btnSaveModify = new JButton();
						panelModifyTopic.add(btnSaveModify);
						btnSaveModify.setBounds(351, 217, 91, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.setAction(getAppActionMap().get("Modify"));
						btnSaveModify.setText(ApplicationInternationalization.getString("btnModify"));
					}
					{
						panelTopicInfoModify = new JPTopicInfo(parentD);
						panelModifyTopic.add(panelTopicInfoModify);
						panelTopicInfoModify.setBounds(12, 41, 433, 165);
						panelTopicInfoModify.setSize(531, 172);
						panelTopicInfoModify.setPreferredSize(new java.awt.Dimension(531, 172));
					}
					{
						lblTopics = new JLabel();
						panelModifyTopic.add(lblTopics);
						lblTopics.setBounds(12, 12, 62, 16);
						lblTopics.setName("lblTopics");
						lblTopics.setText(ApplicationInternationalization.getString("lblTopics"));
					}
					{
						cbTopics = new JComboBox();
						panelModifyTopic.add(cbTopics);
						cbTopics.setBounds(73, 9, 161, 23);
						cbTopics.setSize(167, 23);
						cbTopics.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cbTopicsActionPerformed();
							}
						});
					}
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
			setItemsComboTopics();
			
			// Hide tabs not available 
			List<String> operationsId = OperationsUtilities.getAllOperations(ClientController.getInstance().getAvailableOperations());
			if (!operationsId.contains(Operations.Add.name()))
				tabPanelTopic.remove(panelAddTopic);
			if (!operationsId.contains(Operations.Modify.name()))
				tabPanelTopic.remove(panelModifyTopic);
			
			// If this panel is invoked by knowledge view, with an item already selected, fill the data
			if (data != null) {
				fillData();
				// Disable tabs not used for that operation
				int indexModify = getIndexTab(ApplicationInternationalization.getString("tabManageTopic_Modify"));
				int indexAdd = getIndexTab(ApplicationInternationalization.getString("tabManageTopic_Add"));
				if (operationToDo.equals(Operations.Add.name())) {
					if (indexModify != -1)
						tabPanelTopic.setEnabledAt(indexModify, false);
					if (indexAdd != -1)
						tabPanelTopic.setSelectedIndex(indexAdd);
				}
				else if (operationToDo.equals(Operations.Modify.name())) {
					if (indexAdd != -1)
						tabPanelTopic.setEnabledAt(indexAdd, false);
					if (indexModify != -1)
						tabPanelTopic.setSelectedIndex(indexModify);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentD, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cbTopicsActionPerformed() {
		panelTopicInfoModify.fillData(topics.get(cbTopics.getSelectedIndex()));
	}
	
	private void setItemsComboTopics() {
		// Get topics
		try {
			topics = ClientController.getInstance().getTopicsWrapper().getTopics();
			if (topics.size() == 0)
				JOptionPane.showMessageDialog(parentD, ApplicationInternationalization.getString("panelManageAnswer_NotTopics"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			for (int i=0; i<topics.size(); i++) {
				cbTopics.insertItemAt(topics.get(i).getTitle(), i);
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
	
	private void fillData() {
		// Fill fields with the received topic
		if (operationToDo.equals(Operations.Modify.name())) {
			Topic t = (Topic)data;
			panelTopicInfoModify.fillData(t);
		}	
		
	}
	
	/*** Actions for buttons ***/
	@Action
	public void Cancel () {
		parentD.dispose();
	}	
	
	@Action
	public void Save() {
		Topic newTopic = new Topic(panelTopicInfoAdd.getTopicTitle(), panelTopicInfoAdd.getTopicDescription(), new Date());
		try {
			// Create and insert new Topic
			Topic newTopicAdded = ClientController.getInstance().addTopic(newTopic);
			// Notify to main frame the new knowledge
			mainFrame.notifyKnowledgeAdded(newTopicAdded, null);
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
		Topic oldTopic = topics.get(cbTopics.getSelectedIndex());
		Topic newTopic = (Topic) oldTopic.clone();
		newTopic.setTitle(panelTopicInfoModify.getTopicTitle());
		newTopic.setDescription(panelTopicInfoModify.getTopicDescription());
		newTopic.setId(oldTopic.getId());
		try {
			// Modify the old Topic
			Topic newTopicModified = ClientController.getInstance().modifyTopic(newTopic, topics.get(cbTopics.getSelectedIndex()));
			// Notify to main frame the new knowledge
			mainFrame.notifyKnowledgeEdited(newTopicModified, oldTopic);
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
    	for(int i=0; i<tabPanelTopic.getTabCount() && result==-1; i++) {
    		if (tabPanelTopic.getTitleAt(i).equals(title))
    			result=i;
    	}
    	return result;
	}

}
