package presentation;

import internationalization.ApplicationInternationalization;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import model.business.knowledge.Project;
import model.business.knowledge.User;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.CheckListRenderer;
import presentation.customComponents.CheckableItem;
import presentation.customComponents.panelProjectInformation;
import presentation.customComponents.txtUserInformation;
import resources.CursorUtilities;
import resources.ImagesUtilities;
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
public class JDManageProject extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6500564343875421781L;
	private panelProjectInformation panelProjectInformationCreate;
	private JPanel pnlUsersCreate;
	private JList listUsers;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JComboBox cbProjects;
	private JLabel lblProject;
	private panelProjectInformation panelProjectInformationModify;
	private JLabel lblUserInfoModify;
	private JList listUsersModify;
	private JScrollPane jScrollPane4;
	private txtUserInformation txtUserInfoModify;
	private JScrollPane jScrollPane3;
	private JPanel panelUsersModify;
	private JButton btnCancelModify;
	private JButton btnModify;
	private JPanel tabModifyProject;
	private JLabel lblUserInfo;
	private JTabbedPane jTabbedPane;
	private JPanel tabCreateProject;
	private txtUserInformation txtUserInfo;
	private JButton btnOKCreate;
	private JButton btnCancelCreate;
	
	private List<User> selectedUsersCreate;
	private List<User> selectedUsersModify;	
	private DefaultListModel modelCreate;
	private DefaultListModel modelModify;

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public JDManageProject(JFrame frame) {
		super(frame);
		selectedUsersCreate = new ArrayList<User>();
		selectedUsersModify = new ArrayList<User>();
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
                  closeWin();
			}
         });
		
		initGUI();
	}

	protected void closeWin() {
		this.dispose();
		
	}
	
	private ActionMap getAppActionMap() {
	        return Application.getInstance().getContext().getActionMap(this);
	}
	
	private void initGUI() {
		try {
			setIconImage(ImagesUtilities.loadCompatibleImage("icono.png"));
		} catch (IOException e1) { }
		
		try {
			this.setTitle(ApplicationInternationalization.getString("manageProjectTitle"));
			{
				setResizable(false);
				getContentPane().setLayout(null);
				{
					jTabbedPane = new JTabbedPane();					
					getContentPane().add(jTabbedPane);
					jTabbedPane.setBounds(0, 12, 637, 564);
					{
						tabCreateProject = new JPanel();
						jTabbedPane.addTab(ApplicationInternationalization.getString("tabCreateProject"), null, tabCreateProject, null);
						tabCreateProject.setBounds(12, 12, 545, 474);
						tabCreateProject.setLayout(null);
						tabCreateProject.setPreferredSize(new java.awt.Dimension(625, 536));
						{
							panelProjectInformationCreate = new panelProjectInformation();
							tabCreateProject.add(panelProjectInformationCreate);
							panelProjectInformationCreate.setBounds(12, 12, 351, 450);
						}
						{
							pnlUsersCreate = new JPanel();
							tabCreateProject.add(pnlUsersCreate);
							pnlUsersCreate.setBounds(369, 12, 256, 450);
							pnlUsersCreate.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelUsers")));
							pnlUsersCreate.setLayout(null);
							{
								jScrollPane2 = new JScrollPane();
								pnlUsersCreate.add(jScrollPane2);
								jScrollPane2.setBounds(17, 311, 223, 125);
								{
									txtUserInfo = new txtUserInformation();
									jScrollPane2.setViewportView(txtUserInfo);
									txtUserInfo.setEditable(false);
								}
							}
							{
								jScrollPane1 = new JScrollPane();
								pnlUsersCreate.add(jScrollPane1);
								jScrollPane1.setBounds(17, 21, 217, 256);
								{
									listUsers = new JList();
									jScrollPane1.setViewportView(listUsers);
									// Set list as check list
									listUsers.setCellRenderer(new CheckListRenderer());
									listUsers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
									listUsers.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											int index = listUsers.locationToIndex(e.getPoint());
											CheckableItem item = (CheckableItem) listUsers.getModel().getElementAt(index);
											item.setSelected(!item.isSelected());
											Rectangle rect = listUsers.getCellBounds(index, index);
											listUsers.repaint(rect);
											txtUserInfo.setText("");
											txtUserInfo.showUserInfo(item.getUser());
											if (item.isSelected()) {
												selectedUsersCreate.add(item.getUser());
											}
											else {
												selectedUsersCreate.remove(item.getUser());
											}
										}
									});
									
								}
							}
							{
								lblUserInfo = new JLabel();
								pnlUsersCreate.add(lblUserInfo);
								lblUserInfo.setBounds(17, 289, 217, 16);
								lblUserInfo.setName("lblUserInfo");
								lblUserInfo.setText(ApplicationInternationalization.getString("userInfo"));
							}

						}
						{
							btnCancelCreate = new JButton();
							tabCreateProject.add(btnCancelCreate);
							btnCancelCreate.setBounds(542, 500, 79, 25);
							btnCancelCreate.setName("btnCancelCreate");
							btnCancelCreate.setAction(getAppActionMap().get("Cancel"));
							btnCancelCreate.setText(ApplicationInternationalization.getString("CancelButton"));
						}
						{
							btnOKCreate = new JButton();
							tabCreateProject.add(btnOKCreate);
							btnOKCreate.setBounds(449, 500, 79, 25);
							btnOKCreate.setName("btnOKCreate");
							btnOKCreate.setAction(getAppActionMap().get("Create"));
							btnOKCreate.setText(ApplicationInternationalization.getString("btnSave"));
						}
					}
					{
						tabModifyProject = new JPanel();
						jTabbedPane.addTab(ApplicationInternationalization.getString("tabModifyProject"), null, tabModifyProject, null);
						tabModifyProject.setLayout(null);
						{
							panelProjectInformationModify = new panelProjectInformation();
							tabModifyProject.add(panelProjectInformationModify);
							panelProjectInformationModify.setBounds(11, 44, 352, 439);
							panelProjectInformationModify.setName("panelProjectInformationModify");
						}
						{
							panelUsersModify = new JPanel();
							tabModifyProject.add(panelUsersModify);
							panelUsersModify.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelUsers")));
							panelUsersModify.setBounds(369, 42, 257, 441);
							panelUsersModify.setLayout(null);
							panelUsersModify.setName("panelUsersModify");
							{
								jScrollPane3 = new JScrollPane();
								panelUsersModify.add(jScrollPane3);
								jScrollPane3.setBounds(17, 311, 223, 120);
								{
									txtUserInfoModify = new txtUserInformation();
									jScrollPane3.setViewportView(txtUserInfoModify);
									txtUserInfoModify.setName("txtUserInfoModify");
								}
							}
							{
								jScrollPane4 = new JScrollPane();
								panelUsersModify.add(jScrollPane4);
								jScrollPane4.setBounds(17, 21, 223, 262);
								{
									listUsersModify = new JList();
									jScrollPane4.setViewportView(listUsersModify);
									listUsersModify.setCellRenderer(new CheckListRenderer());
									listUsersModify.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											int index = listUsersModify.locationToIndex(e.getPoint());
											CheckableItem item = (CheckableItem) listUsersModify.getModel().getElementAt(index);
											item.setSelected(!item.isSelected());
											Rectangle rect = listUsersModify.getCellBounds(index, index);
											listUsersModify.repaint(rect);
											txtUserInfoModify.setText("");
											txtUserInfoModify.showUserInfo(item.getUser());
											if (item.isSelected()) {
												selectedUsersModify.add(item.getUser());
											}
											else {
												selectedUsersModify.remove(item.getUser());
											}
										}
									});
								}
							}
							{
								lblUserInfoModify = new JLabel();
								panelUsersModify.add(lblUserInfoModify);
								lblUserInfoModify.setName("lblUserInfo");
								lblUserInfoModify.setBounds(17, 289, 217, 16);
								lblUserInfoModify.setText(ApplicationInternationalization.getString("userInfo"));
							}
						}
						{
							btnCancelModify = new JButton();
							tabModifyProject.add(btnCancelModify);
							btnCancelModify.setAction(getAppActionMap().get("Cancel"));
							btnCancelModify.setName("btnCancelCreate");
							btnCancelModify.setBounds(547, 500, 79, 25);
							btnCancelModify.setText(ApplicationInternationalization.getString("CancelButton"));
						}
						{
							btnModify = new JButton();
							tabModifyProject.add(btnModify);
							btnModify.setAction(getAppActionMap().get("Modify"));
							btnModify.setName("btnOKCreate");
							btnModify.setBounds(454, 500, 79, 25);
							btnModify.setText(ApplicationInternationalization.getString("btnModify"));
						}
						{
							lblProject = new JLabel();
							tabModifyProject.add(lblProject);
							lblProject.setBounds(12, 12, 65, 16);
							lblProject.setName("lblProject");
							lblProject.setText(ApplicationInternationalization.getString("selectProject"));
						}
						{
							cbProjects = new JComboBox();
							tabModifyProject.add(cbProjects);
							cbProjects.setBounds(89, 9, 201, 23);
							cbProjects.addActionListener(new ActionListener() {								
								@Override
								public void actionPerformed(ActionEvent e) {
									txtUserInfoModify.setText("");
									fillProjectData();		
									showUsers();
								}
							});
						}
					}
				}
				
				btnCancelModify.setEnabled(false);
				btnModify.setEnabled(false);

				fillUsers();
				fillProjects();
				
				panelProjectInformationCreate.showData(new Project(), true, true, 180);
				cbProjects.setSelectedIndex(0);
				
			}
			this.setSize(643, 605);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void showUsers() {
		if (cbProjects.getSelectedItem() != null) {
			try {
				List<User> users = ClientController.getInstance().getUsersProject((Project) cbProjects.getSelectedItem());
				for (User u: users) {
					selectUserInList(getIndexofUserInList(u));
				}
				selectedUsersModify.addAll(users);
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
	}

	private void selectUserInList(int index) {
		CheckableItem item = (CheckableItem) listUsersModify.getModel().getElementAt(index);
		item.setSelected(true);
		Rectangle rect = listUsersModify.getCellBounds(index, index);
		listUsersModify.repaint(rect);
	}
	
	private int getIndexofUserInList(User u) {
		boolean found = false;
		int result = -1;
		for(int i=0; i<modelModify.getSize() && !found; i++) {
			CheckableItem item = (CheckableItem) modelModify.get(i);
			if(item.getUser().equals(u)) {
				result = i;
				found = true;
			}
		}
		return result;
	}

	private void fillProjects() {
		try {
			List<Project> projects = ClientController.getInstance().getProjectsFromCurrentUser();
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			int cont = 0;
			for(Project p: projects) {
				model.addElement(p);
				cont++;
			}
			cbProjects.setModel(model);
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
	
	private void fillProjectData() {
		if (cbProjects.getSelectedItem() != null) {
			panelProjectInformationModify.showData((Project) cbProjects.getSelectedItem(), true, true, 180);
			btnCancelModify.setEnabled(true);
			btnModify.setEnabled(true);
		}		
	}

	// Method used to show users
	private void fillUsers() {
		try {
			List<User> users = ClientController.getInstance().getUsers();
			modelCreate = new DefaultListModel();
			modelModify = new DefaultListModel();
			int cont = 0;
			for(User u: users) {
				modelCreate.add(cont, new CheckableItem(u));
				modelModify.add(cont, new CheckableItem(u));
				cont++;
			}
			listUsers.setModel(modelCreate);
			listUsersModify.setModel(modelModify);
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
	
	@Action
	public void Create() {
		CursorUtilities.showWaitCursor(this);
		if (panelProjectInformationCreate.isValidData(false)) {
			// Create project and update users
			Project newProject = panelProjectInformationCreate.getProject();
			try {
				Project p = ClientController.getInstance().createProject(newProject);
				if (p != null)
					// Update id of the project
					newProject.setId(p.getId());			
				for (User u : selectedUsersCreate) {
	                ClientController.getInstance().addProjectsUser(u, p);
				}
				JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("operationSuccesfully"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
				CursorUtilities.showDefaultCursor(this);
				this.dispose();
			} catch (RemoteException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			CursorUtilities.showDefaultCursor(this);
	}
	
	@Action
	public void Modify() {
		CursorUtilities.showWaitCursor(this);
		if (panelProjectInformationCreate.isValidData(false)) {
			// Modify project and update users
			Project oldProject = (Project)cbProjects.getSelectedItem();
			Project projectModified = panelProjectInformationModify.getProject();
			projectModified.setId(oldProject.getId());
			try {
				ClientController.getInstance().updateProject(projectModified);
				List<User> oldUsers = ClientController.getInstance().getUsersProject(oldProject);
				// Update users
				// Add new users, if any
				for (User u : selectedUsersModify) {
					if (!oldUsers.contains(u)) {
						ClientController.getInstance().addProjectsUser(u, projectModified);
					}
				}
				// Remove old users, if any
				for (User u : oldUsers) {
					if (!selectedUsersModify.contains(u)) {
						ClientController.getInstance().removeProjectsUser(u, projectModified);
					}
				}
				JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("operationSuccesfully"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
				CursorUtilities.showDefaultCursor(this);
				this.dispose();
			} catch (RemoteException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				CursorUtilities.showDefaultCursor(this);
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			CursorUtilities.showDefaultCursor(this);
	}
	
	@Action
	public void Cancel() {
		this.dispose();
	}

}
