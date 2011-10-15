package presentation;
import internationalization.ApplicationInternationalization;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.business.knowledge.Project;
import model.business.knowledge.User;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import exceptions.NonPermissionRoleException;
import exceptions.NotLoggedException;

import bussiness.control.ClientController;
import presentation.customComponents.CheckListRenderer;
import presentation.customComponents.CheckableItem;

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
	private panelProjectInformation panelCaseInformationCreate;
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
	private JTextArea txtUserInfoModify;
	private JScrollPane jScrollPane3;
	private JPanel panelUsersModify;
	private JButton btnCancelModify;
	private JButton btnModify;
	private JPanel tabModifyProject;
	private JLabel lblUserInfo;
	private JTabbedPane jTabbedPane;
	private JPanel tabCreateProject;
	private JTextArea txtUserInfo;
	private JButton btnOKCreate;
	private JButton btnCancelCreate;
	
	private List<User> selectedUsersCreate;
	private List<User> selectedUsersModify;
	private DefaultListModel model;

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public JDManageProject(JFrame frame) {
		super(frame);
		selectedUsersCreate = new ArrayList<User>();
		selectedUsersModify = new ArrayList<User>();
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
	        return Application.getInstance().getContext().getActionMap(this);
	}
	
	private void initGUI() {
		try {
			this.setResizable(false);
			this.setTitle(ApplicationInternationalization.getString("manageProjectTitle"));
			{
				getContentPane().setLayout(null);
				{
					jTabbedPane = new JTabbedPane();					
					getContentPane().add(jTabbedPane);
					jTabbedPane.setBounds(12, 12, 562, 564);
					{
						tabCreateProject = new JPanel();
						jTabbedPane.addTab(ApplicationInternationalization.getString("tabCreateProject"), null, tabCreateProject, null);
						tabCreateProject.setBounds(12, 12, 545, 474);
						tabCreateProject.setLayout(null);
						tabCreateProject.setPreferredSize(new java.awt.Dimension(617, 564));
						{
							panelCaseInformationCreate = new panelProjectInformation();
							tabCreateProject.add(panelCaseInformationCreate);
							panelCaseInformationCreate.setBounds(12, 12, 266, 450);
						}
						{
							pnlUsersCreate = new JPanel();
							tabCreateProject.add(pnlUsersCreate);
							pnlUsersCreate.setBounds(290, 12, 257, 450);
							pnlUsersCreate.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelUsers")));
							pnlUsersCreate.setLayout(null);
							{
								jScrollPane2 = new JScrollPane();
								pnlUsersCreate.add(jScrollPane2);
								jScrollPane2.setBounds(17, 311, 223, 125);
								{
									txtUserInfo = new JTextArea();
									jScrollPane2.setViewportView(txtUserInfo);
									txtUserInfo.setBounds(17, 306, 220, 92);
									txtUserInfo.setWrapStyleWord(true);
									txtUserInfo.setLineWrap(true);
									txtUserInfo.setEditable(false);
									txtUserInfo.setPreferredSize(new java.awt.Dimension(234, 94));
								}
							}
							{
								jScrollPane1 = new JScrollPane();
								pnlUsersCreate.add(jScrollPane1);
								jScrollPane1.setBounds(17, 21, 217, 250);
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
											if (item.isSelected()) {
												txtUserInfo.append(ApplicationInternationalization.getString("user") + ": " + item.getUser().getName() +", " + item.getUser().getSurname()
														+ "\n" + ApplicationInternationalization.getString("company") + ": " + item.getUser().getCompany().getName() + ", " + item.getUser().getCompany().getAddress().getCity() + "(" 
																+ item.getUser().getCompany().getAddress().getCountry() + ")" + "\n\n");
												Font f = txtUserInfo.getFont();
												txtUserInfo.setFont(new Font(f.getName(), f.getStyle(), 11));
												selectedUsersCreate.add(item.getUser());
											}
											else {
												txtUserInfo.setText("");
												selectedUsersCreate.remove(item.getUser());
											}
										}
									});
									
									listUsers.setBounds(5, 131, 247, 128);
									listUsers.setPreferredSize(new java.awt.Dimension(213, 232));
								}
							}
							{
								lblUserInfo = new JLabel();
								pnlUsersCreate.add(lblUserInfo);
								lblUserInfo.setBounds(17, 283, 47, 16);
								lblUserInfo.setName("lblUserInfo");
								lblUserInfo.setText(ApplicationInternationalization.getString("userInfo"));
							}

						}
						{
							btnCancelCreate = new JButton();
							tabCreateProject.add(btnCancelCreate);
							btnCancelCreate.setBounds(467, 500, 79, 25);
							btnCancelCreate.setName("btnCancelCreate");
							btnCancelCreate.setAction(getAppActionMap().get("Cancel"));
							btnCancelCreate.setText(ApplicationInternationalization.getString("CancelButton"));
						}
						{
							btnOKCreate = new JButton();
							tabCreateProject.add(btnOKCreate);
							btnOKCreate.setBounds(372, 500, 79, 25);
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
							panelProjectInformationModify.setBounds(12, 53, 266, 430);
							panelProjectInformationModify.setName("panelProjectInformationModify");
						}
						{
							panelUsersModify = new JPanel();
							tabModifyProject.add(panelUsersModify);
							panelUsersModify.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelUsers")));
							panelUsersModify.setBounds(290, 46, 257, 442);
							panelUsersModify.setLayout(null);
							panelUsersModify.setName("panelUsersModify");
							{
								jScrollPane3 = new JScrollPane();
								panelUsersModify.add(jScrollPane3);
								jScrollPane3.setBounds(17, 311, 223, 120);
								{
									txtUserInfoModify = new JTextArea();
									jScrollPane3.setViewportView(txtUserInfoModify);
									txtUserInfoModify.setPreferredSize(new java.awt.Dimension(220, 117));
									txtUserInfoModify.setBounds(17, 306, 220, 92);
									txtUserInfoModify.setName("txtUserInfoModify");
								}
							}
							{
								jScrollPane4 = new JScrollPane();
								panelUsersModify.add(jScrollPane4);
								jScrollPane4.setBounds(17, 21, 217, 250);
								{
									ListModel jList1Model = 
										new DefaultComboBoxModel(
												new String[] { "Item One", "Item Two" });
									listUsersModify = new JList();
									jScrollPane4.setViewportView(listUsersModify);
									listUsersModify.setModel(jList1Model);
									listUsersModify.setCellRenderer(new CheckListRenderer());
									listUsersModify.setPreferredSize(new java.awt.Dimension(213, 232));
									listUsersModify.setBounds(5, 131, 247, 128);
									listUsersModify.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											int index = listUsers.locationToIndex(e.getPoint());
											CheckableItem item = (CheckableItem) listUsers.getModel().getElementAt(index);
											item.setSelected(!item.isSelected());
											Rectangle rect = listUsers.getCellBounds(index, index);
											listUsers.repaint(rect);
											if (item.isSelected()) {
												txtUserInfo.append(ApplicationInternationalization.getString("user") + ": " + item.getUser().getName() +", " + item.getUser().getSurname()
														+ "\n" + ApplicationInternationalization.getString("company") + ": " + item.getUser().getCompany().getName() + ", " + item.getUser().getCompany().getAddress().getCity() + "(" 
																+ item.getUser().getCompany().getAddress().getCountry() + ")" + "\n\n");
												Font f = txtUserInfo.getFont();
												txtUserInfo.setFont(new Font(f.getName(), f.getStyle(), 11));
												selectedUsersModify.add(item.getUser());
											}
											else {
												txtUserInfo.setText("");
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
								lblUserInfoModify.setBounds(17, 283, 47, 16);
								lblUserInfoModify.setText(ApplicationInternationalization.getString("userInfo"));
							}
						}
						{
							btnCancelModify = new JButton();
							tabModifyProject.add(btnCancelModify);
							btnCancelModify.setAction(getAppActionMap().get("Cancel"));
							btnCancelModify.setName("btnCancelCreate");
							btnCancelModify.setBounds(467, 500, 79, 25);
							btnCancelModify.setText(ApplicationInternationalization.getString("CancelButton"));
						}
						{
							btnModify = new JButton();
							tabModifyProject.add(btnModify);
							btnModify.setAction(getAppActionMap().get("Modify"));
							btnModify.setName("btnOKCreate");
							btnModify.setBounds(371, 500, 79, 25);
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
				panelCaseInformationCreate.showData(new Project(), true);
			}
			this.setSize(593, 620);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	protected void showUsers() {
		if (cbProjects.getSelectedItem() != null) {
			try {
				List<User> users = ClientController.getInstance().getUsersProject((Project) cbProjects.getSelectedItem());
				for (User u: users) {
					listUsersModify.setSelectedIndex(getIndexofUserInList(u));
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonPermissionRoleException e) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRoleException e) {
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
	
	private void fillProjectData() {
		if (cbProjects.getSelectedItem() != null) {
			panelProjectInformationModify.showData((Project) cbProjects.getSelectedItem(), true);
			panelProjectInformationModify.setEnabled(true);
			panelUsersModify.setEnabled(true);
			btnCancelModify.setEnabled(true);
			btnModify.setEnabled(true);
		}
		
	}

	// Method used to show users
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
			listUsers.setModel(model);
			listUsersModify.setModel(model);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRoleException e) {
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
	
	@Action
	public void Create() {
		// Validate data TODO
		// Create project and update users
		Project newProject = panelCaseInformationCreate.getProject();
		if (newProject == null)
			; // TODO: error. Rellenar datos obligatorios del proyecto	
		try {
			Project p = ClientController.getInstance().createProject(newProject);
			if (p != null)
				// Update id of the project
				newProject.setId(p.getId());			
			for (User u : selectedUsersCreate) {
				ClientController.getInstance().addProjectsUser(u, p);
			}
			this.dispose();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRoleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Action
	public void Modify() {
		// Validate data TODO
		// Modify project and update users
		Project oldProject = (Project)cbProjects.getSelectedItem();
		Project projectModified = panelCaseInformationCreate.getProject();
		projectModified.setId(oldProject.getId());
		if (projectModified == null)
			; // TODO: error. Rellenar datos obligatorios del proyecto	
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
			this.dispose();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonPermissionRoleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Action
	public void Cancel() {
		this.dispose();
	}

}
