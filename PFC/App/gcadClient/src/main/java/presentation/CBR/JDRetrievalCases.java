package presentation.CBR;

import internationalization.ApplicationInternationalization;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import model.business.knowledge.KnowledgeStatus;
import model.business.knowledge.Project;
import model.business.knowledge.User;

import org.japura.gui.ArrowButton;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.CheckListRenderer;
import presentation.customComponents.CheckableItem;
import presentation.customComponents.panelProjectInformation;
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
	private JButton btnExport;
	private JButton btnSave;
	private JToolBar toolbar;
	private JPanel pnlProject;
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
		setTitle(ApplicationInternationalization.getString("retrievalCases"));
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
			panelTree.showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1)), KnowledgeStatus.All);
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
					lblNumberCases = new JLabel();
					getContentPane().add(lblNumberCases);
					lblNumberCases.setBounds(12, 552, 128, 24);
					lblNumberCases.setName("lblNumberCases");					
					lblNumberCases.setText(ApplicationInternationalization.getString("lblNumberCases") + " " + currentProject+"/"+cases.size());
				}
				{
					btnForward = new ArrowButton(ArrowButton.DOUBLE_RIGHT);
					getContentPane().add(btnForward);
					btnForward.setBounds(187, 561, 20, 17);
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
					btnBackward.setBounds(152, 561, 23, 26);
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
					btnOk.setBounds(820, 550, 82, 27);
					btnOk.setName("btnOk");
					btnOk.setAction(getAppActionMap().get("OK"));
					btnOk.setText(ApplicationInternationalization.getString("btnOK"));
				}
				{
					panelTree = new panelKnowledgeTree();
					getContentPane().add(panelTree);
					panelTree.setBounds(623, 34, 283, 510);
				}
				{
					pnlProject = new JPanel();
					getContentPane().add(pnlProject);
					pnlProject.setBounds(4, 34, 639, 507);
					pnlProject.setLayout(null);
					{
						currentPanel = new panelProjectInformation();
						pnlProject.add(currentPanel);
						currentPanel.setBounds(0, 0, 357, 503);
					}
					{
						pnlUsersCreate = new JPanel();
						pnlProject.add(pnlUsersCreate);
						pnlUsersCreate.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelUsers")));
						pnlUsersCreate.setLayout(null);
						pnlUsersCreate.setBounds(363, 0, 243, 506);
						{
							jScrollPane2 = new JScrollPane();
							pnlUsersCreate.add(jScrollPane2);
							jScrollPane2.setBounds(12, 346, 222, 149);
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
							jScrollPane1.setBounds(12, 21, 222, 291);
							{
								listUsers = new JList();
								jScrollPane1.setViewportView(listUsers);
								listUsers.setCellRenderer(new CheckListRenderer());
								listUsers.setPreferredSize(new java.awt.Dimension(213,232));
								listUsers.setBounds(5, 131, 247, 128);
								listUsers.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										int index = listUsers.locationToIndex(e.getPoint());
										CheckableItem item = (CheckableItem) listUsers.getModel().getElementAt(index);
										txtUserInfo.setText("");
										txtUserInfo.append(ApplicationInternationalization.getString("user") + ": " + item.getUser().getName() +", " + item.getUser().getSurname()
												+ "\n" + ApplicationInternationalization.getString("company") + ": " + item.getUser().getCompany().getName() + ", " + item.getUser().getCompany().getAddress().getCity() + "(" 
														+ item.getUser().getCompany().getAddress().getCountry() + ")" + "\n\n");
										Font f = txtUserInfo.getFont();
										txtUserInfo.setFont(new Font(f.getName(), f.getStyle(), 11));
									}
								});
							}
						}
						{
							lblUserInfo = new JLabel();
							pnlUsersCreate.add(lblUserInfo);
							lblUserInfo.setName("lblUserInfo");
							lblUserInfo.setBounds(12, 324, 110, 15);
							lblUserInfo.setText(ApplicationInternationalization.getString("lblUserInfo"));
						}
					}
				}
				{
					toolbar = new JToolBar();
					getContentPane().add(toolbar);
					toolbar.setBounds(0, 0, 913, 28);
					{
						btnSave = new JButton();
						toolbar.add(btnSave);
						btnSave.setPreferredSize(new java.awt.Dimension(28, 28));
						btnSave.setSize(28, 28);
						setToolbarButtonProperties(btnSave, "SaveAsPDF");
					}
					{
						btnExport = new JButton();
						toolbar.add(btnExport);
						btnExport.setPreferredSize(new java.awt.Dimension(28, 28));
						btnExport.setSize(28, 28);
						setToolbarButtonProperties(btnSave, "ExportXML");
					}
				}
				{
					
				}
			}
			this.setSize(929, 624);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setToolbarButtonProperties(JButton button, String id) {
		button.setBorder(BorderFactory.createEmptyBorder(5,10,4,10));
    	button.setFocusPainted(false);
    	button.setHorizontalTextPosition(SwingConstants.CENTER);
    	button.setAction(getAppActionMap().get(id));
    	button.setText("");
    	button.setVerticalTextPosition(SwingConstants.BOTTOM);
    	button.setRequestFocusEnabled(false);
    	button.setName(id);
    	button.setToolTipText(ApplicationInternationalization.getString("Tooltip_" + id));
    	BufferedImage image = null;
    	try {
			image = ImagesUtilities.loadCompatibleImage("Toolbars/" + id + ".png");
			if (image != null) {
				button.setIcon(new ImageIcon(image));
				// Save button icon
				ImagesUtilities.addImageButton(button.getName(), image);
			}			
		} catch (Exception e) { }	  
		
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
			listUsers.setModel(model);
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
	
	@Action
	public void OK() {
		this.dispose();	
	}
}
