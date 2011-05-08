package presentation.panelsManageKnowledge;

import internationalization.ApplicationInternationalization;

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

	public JPManageTopic(JDialog parent) {
		super();
		this.parentD = parent;
		data = null;
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	public JPManageTopic(JDialog parent, Object data, String operationToDo) {
		super();
		this.data = data;
		this.operationToDo = operationToDo;
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
						btnCancelAdd.setAction(getAppActionMap().get("Cancel"));
						btnCancelAdd.setText(ApplicationInternationalization.getString("CancelButton"));						
					}
					{
						btnSaveTopic = new JButton();
						panelAddTopic.add(btnSaveTopic, new AnchorConstraint(835, 754, 929, 596, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						btnSaveTopic.setName("btnSaveTopic");
						btnSaveTopic.setBounds(297, 217, 68, 24);
						btnSaveTopic.setAction(getAppActionMap().get("Save"));
						btnSaveTopic.setText(ApplicationInternationalization.getString("btnSave"));
					}
					{
						panelTopicInfoAdd = new JPTopicInfo(parentD);
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
						btnCancelAdd.setAction(getAppActionMap().get("Cancel"));
						btnCancelAdd.setText(ApplicationInternationalization.getString("CancelButton"));
					}
					{
						btnSaveModify = new JButton();
						panelModifyTopic.add(btnSaveModify);
						btnSaveModify.setBounds(297, 217, 68, 24);
						btnSaveModify.setName("btnSaveModify");
						btnSaveModify.setAction(getAppActionMap().get("Modify"));
						btnSaveModify.setText(ApplicationInternationalization.getString("btnModify"));
					}
					{
						panelTopicInfoModify = new JPTopicInfo(parentD);
						panelModifyTopic.add(panelTopicInfoModify);
						panelTopicInfoModify.setBounds(12, 41, 433, 165);
					}
					{
						lblTopics = new JLabel();
						panelModifyTopic.add(lblTopics);
						lblTopics.setBounds(12, 12, 49, 16);
						lblTopics.setName("lblTopics");
						lblTopics.setText(ApplicationInternationalization.getString("lblTopics"));
					}
					{
						cbTopics = new JComboBox();
						panelModifyTopic.add(cbTopics);
						cbTopics.setBounds(73, 9, 161, 23);
					}
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
			
			setItemsComboTopics();
			
			// Hide tabs not available 
			List<String> operationsId = OperationsUtilities.getAllOperationsId(ClientController.getInstance().getAvailableOperations());
			if (!operationsId.contains("Add"))
				tabPanelTopic.remove(panelAddTopic);
			if (!operationsId.contains("Modify"))
				tabPanelTopic.remove(panelModifyTopic);
			
			// If this panel is invoked by knowledge view, with an item already selected, fill the data
			if (data != null)
				fillData();
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
			for (int i=0; i<topics.size(); i++) {
				cbTopics.insertItemAt(topics.get(i).getTitle(), i);
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
	
	private void fillData() {
		// Fill fields with the received topic
		if (operationToDo.equals("Modify")) {
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
			ClientController.getInstance().addTopic(newTopic);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Action
	public void Modify() {
		Topic newTopic = new Topic(panelTopicInfoAdd.getTopicTitle(), panelTopicInfoAdd.getTopicDescription(), new Date());
		try {
			// Modify the old Topic
			ClientController.getInstance().modifyTopic(newTopic, topics.get(cbTopics.getSelectedIndex()));
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
