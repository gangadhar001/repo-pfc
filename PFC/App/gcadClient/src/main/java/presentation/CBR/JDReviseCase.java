package presentation.CBR;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultTreeModel;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.panelProjectInformation;
import bussiness.control.ClientController;
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
public class JDReviseCase extends javax.swing.JDialog {
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
	private JLabel lblInfo;
	private JButton btnOK;
	private JButton btnCancel;
	private int currentProject;
	private DefaultTreeModel treeModel;
	private JPanel panelKnowledge;
	private JTextArea txtAreaKnowledge;
	private panelKnowledgeTree panelTree;
	
	public JDReviseCase(JFrame frame, List<Project> cases) {
		super(frame);
		this.cases = cases;
		currentProject = 1;
		initGUI();
		currentPanel.showData(cases.get(currentProject-1), true);
		// Show tree of knowledge for this project
		try {
			panelTree.showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)));
			panelTree.setEditable();
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
	
	
	
	private void initGUI() {		
		try {
			{
				getContentPane().setLayout(null);
				{
					currentPanel = new panelProjectInformation();
					getContentPane().add(currentPanel);
					currentPanel.setBounds(12, 12, 272, 480);
				}
				{
					lblNumberCases = new JLabel();
					getContentPane().add(lblNumberCases);
					lblNumberCases.setBounds(110, 507, 79, 16);
					lblNumberCases.setName("lblNumberCases");
					lblNumberCases.setText("Case " + currentProject+"/"+cases.size());
				}
				{
					btnForward = new JButton();
					getContentPane().add(btnForward);
					btnForward.setBounds(195, 504, 45, 23);
					btnForward.setAction(getAppActionMap().get("Forward"));
					btnForward.setName("btnForward");
				}
				{
					btnBackward = new JButton();
					getContentPane().add(btnBackward);
					btnBackward.setBounds(56, 504, 43, 23);
					btnBackward.setAction(getAppActionMap().get("Backward"));
					btnBackward.setName("btnBackward");
					btnBackward.setEnabled(false);
				}
				{
					btnCancel = new JButton();
					getContentPane().add(btnCancel);
					btnCancel.setBounds(439, 543, 82, 25);
					btnCancel.setName("btnCancel");
				}
				{
					btnOK = new JButton();
					getContentPane().add(btnOK);
					btnOK.setBounds(346, 543, 82, 25);
					btnOK.setName("btnOK");
				}
				{
					panelTree = new panelKnowledgeTree();
					getContentPane().add(panelTree);
					panelTree.setBounds(296, 12, 227, 480);
				}
				{
					
				}
			}
			this.setSize(551, 618);
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
			currentPanel.showData(cases.get(currentProject-1), true);
			try {
				panelTree.showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)));
				panelTree.setEditable();
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
			currentPanel.showData(cases.get(currentProject-1), true);
			try {
				panelTree.showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)));
				panelTree.setEditable();
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
