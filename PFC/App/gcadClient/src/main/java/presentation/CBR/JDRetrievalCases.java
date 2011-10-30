package presentation.CBR;

import internationalization.ApplicationInternationalization;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.business.knowledge.Project;
import model.business.knowledge.User;

import org.japura.gui.ArrowButton;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.CheckListRenderer;
import presentation.customComponents.CheckableItem;
import presentation.customComponents.panelProjectInformation;
import bussiness.control.ClientController;
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
public class JDRetrievalCases extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5940879165571381847L;
	private panelProjectInformation currentPanel;
	private ArrowButton btnBackward;
	private ArrowButton btnForward;
	private JLabel lblNumberCases;	
	private List<Project> cases;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JLabel lblUserInfo;
	private JList listUsers;
	private JTextArea txtUserInfo;
	private JPanel pnlUsersCreate;
	private JButton btnOk;
	private int currentProject;
	private panelKnowledgeTree panelTree;
	private DefaultListModel model;
	
	public JDRetrievalCases(List<Project> cases) {
		super();
		this.cases = cases;
		currentProject = 1;
		initGUI();
		// Show information about the case (project)
		showCaseInformation();
	}	
	
	// Method used to show information about the case (project)
	private void showCaseInformation() {
		currentPanel.showData(cases.get(currentProject-1), false);
		fillUsers();
		showUsers(cases.get(currentProject-1));
		try {
			panelTree.showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)));
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
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
					currentPanel = new panelProjectInformation(this);
					getContentPane().add(currentPanel);
					currentPanel.setBounds(12, 12, 272, 480);
				}
				{
					lblNumberCases = new JLabel();
					getContentPane().add(lblNumberCases);
					lblNumberCases.setBounds(12, 541, 95, 13);
					lblNumberCases.setName("lblNumberCases");					
					lblNumberCases.setText(ApplicationInternationalization.getString("lblNumberCases") + " " + currentProject+"/"+cases.size());
				}
				{
					btnForward = new ArrowButton(ArrowButton.DOUBLE_RIGHT);
					getContentPane().add(btnForward);
					btnForward.setBounds(183, 543, 45, 23);
					btnForward.addActionListener(new ActionListener() {						
						@Override
						public void actionPerformed(ActionEvent e) {
							Forward();							
						}
					});
					btnForward.setName("btnForward");
				}
				{
					btnBackward = new ArrowButton(ArrowButton.DOUBLE_LEFT);
					getContentPane().add(btnBackward);
					btnBackward.setBounds(150, 543, 27, 23);
					btnBackward.addActionListener(new ActionListener() {						
						@Override
						public void actionPerformed(ActionEvent e) {
							Backward();							
						}
					});
					btnBackward.setName("btnBackward");
					btnBackward.setEnabled(false);
				}
				{
					btnOk = new JButton();
					getContentPane().add(btnOk);
					btnOk.setBounds(764, 542, 82, 25);
					btnOk.setName("btnOk");
					btnOk.setText(ApplicationInternationalization.getString("btnOK"));
				}
				{
					panelTree = new panelKnowledgeTree();
					getContentPane().add(panelTree);
					panelTree.setBounds(620, 0, 227, 500);
				}
				{
					pnlUsersCreate = new JPanel();
					getContentPane().add(pnlUsersCreate);
					pnlUsersCreate.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelUsers")));
					pnlUsersCreate.setLayout(null);
					pnlUsersCreate.setBounds(350, 12, 243, 488);
					{
						jScrollPane2 = new JScrollPane();
						pnlUsersCreate.add(jScrollPane2);
						jScrollPane2.setBounds(12, 329, 222, 137);
						{
							txtUserInfo = new JTextArea();
							jScrollPane2.setViewportView(txtUserInfo);
							txtUserInfo.setPreferredSize(new java.awt.Dimension(219, 130));
							txtUserInfo.setBounds(17, 306, 220, 92);
							txtUserInfo.setName("txtUserInfo");
						}
					}
					{
						jScrollPane1 = new JScrollPane();
						pnlUsersCreate.add(jScrollPane1);
						jScrollPane1.setBounds(12, 21, 222, 268);
						{
							listUsers = new JList();
							jScrollPane1.setViewportView(listUsers);
							listUsers.setCellRenderer(new CheckListRenderer());
							listUsers.setPreferredSize(new java.awt.Dimension(213,232));
							listUsers.setBounds(5, 131, 247, 128);
							listUsers.setEnabled(false);
						}
					}
					{
						lblUserInfo = new JLabel();
						pnlUsersCreate.add(lblUserInfo);
						lblUserInfo.setName("lblUserInfo");
						lblUserInfo.setBounds(12, 301, 112, 16);
						lblUserInfo.setText(ApplicationInternationalization.getString("lblUserInfo"));
					}
				}
				{
					
				}
			}
			this.setSize(873, 618);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method used to show all users
	private void fillUsers() {
		try {
			List<User> users = ClientController.getInstance().getUsers();
			model = new DefaultListModel();
			int cont = 0;
			for(User u: users) {
				CheckableItem chkItem = new CheckableItem(u);
				model.add(cont, chkItem);
				cont++;
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NonPermissionRoleException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	// Method used to show the users that work in a project
	protected void showUsers(Project p) {
	
			try {
				List<User> users = ClientController.getInstance().getUsersProject(p);
				for (User u: users) {
					selectUserInList(getIndexofUserInList(u));
				}
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
				
	}

	private void selectUserInList(int index) {
		CheckableItem item = (CheckableItem) listUsers.getModel().getElementAt(index);
		item.setSelected(!item.isSelected());
		Rectangle rect = listUsers.getCellBounds(index, index);
		listUsers.repaint(rect);
	}
	
	private int getIndexofUserInList(User u) {
		boolean found = false;
		int result = -1;
		for(int i=0; i<model.getSize() && !found; i++) {
			CheckableItem item = (CheckableItem) model.get(i);
			if(item.getUser().equals(u)) {
				result = i;
				found = true;
			}
		}
		return result;
	}

	
	@Action
	public void Forward() {
		// Show the next case, if any
		if (currentProject < cases.size()) {
			currentProject++;
			btnBackward.setEnabled(true);
			showCaseInformation();
			lblNumberCases.setText(ApplicationInternationalization.getString("lblNumberCases") + " " + currentProject+"/"+cases.size());
		}
		if(currentProject == cases.size()) {
			btnForward.setEnabled(false);
		}
	}
	
	@Action
	public void Backward() {
		// Show the previous case, if any
		if (currentProject > 0) {
			currentProject--;
			btnForward.setEnabled(true);
			showCaseInformation();
			lblNumberCases.setText(ApplicationInternationalization.getString("lblNumberCases") + " " + currentProject+"/"+cases.size());
		}
		if(currentProject == 1) {
			btnBackward.setEnabled(false);
		}
	}
}