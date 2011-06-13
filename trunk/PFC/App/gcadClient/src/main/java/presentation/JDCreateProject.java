package presentation;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import model.business.knowledge.Project;
import model.business.knowledge.User;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import exceptions.NonPermissionRole;
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
public class JDCreateProject extends javax.swing.JDialog {
	private panelProjectInformation panelCaseInformation;
	private JPanel pnlUsers;
	private JList listUsers;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JLabel lblUserInfo;
	private JTextArea txtUserInfo;
	private JButton btnOK;
	private JButton btnCancel;

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public JDCreateProject(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
	        return Application.getInstance().getContext().getActionMap(this);
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					panelCaseInformation = new panelProjectInformation();
					getContentPane().add(panelCaseInformation);
					panelCaseInformation.setBounds(12, 12, 266, 415);
				}
				{
					pnlUsers = new JPanel();
					getContentPane().add(pnlUsers);
					pnlUsers.setBounds(290, 12, 257, 415);
					pnlUsers.setBorder(BorderFactory.createTitledBorder(""));
					pnlUsers.setLayout(null);
					{
						jScrollPane2 = new JScrollPane();
						pnlUsers.add(jScrollPane2);
						jScrollPane2.setBounds(10, 312, 235, 94);
						{
							txtUserInfo = new JTextArea();
							jScrollPane2.setViewportView(txtUserInfo);
							txtUserInfo.setBounds(17, 306, 220, 92);
							txtUserInfo.setWrapStyleWord(true);
							txtUserInfo.setLineWrap(true);
							txtUserInfo.setEditable(false);
						}
					}
					{
						jScrollPane1 = new JScrollPane();
						pnlUsers.add(jScrollPane1);
						jScrollPane1.setBounds(10, 17, 235, 256);
						{
							listUsers = new JList();
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
							        	txtUserInfo.setText("User: " + item.getUser().getName() +", " + item.getUser().getSurname()
							        			+ "\nCompany: " + item.getUser().getCompany().getName() + ", " + item.getUser().getCompany().getAddress().getCity() + "(" 
							        			+ item.getUser().getCompany().getAddress().getCountry() + ")");
							        }
							        else {
							        	txtUserInfo.setText("");
							        }
							      }
							});
							
							jScrollPane1.setViewportView(listUsers);
							listUsers.setBounds(17, 17, 223, 347);
						}
					}
					
					{
						lblUserInfo = new JLabel();
						pnlUsers.add(lblUserInfo);
						lblUserInfo.setBounds(10, 285, 74, 14);
						lblUserInfo.setName("lblUserInfo");
					}
				}
				{
					btnCancel = new JButton();
					getContentPane().add(btnCancel);
					btnCancel.setBounds(468, 448, 79, 23);
					btnCancel.setName("btnCancel");
					btnCancel.setSize(79, 25);
					btnCancel.setAction(getAppActionMap().get("Cancel"));
				}
				{
					btnOK = new JButton();
					getContentPane().add(btnOK);
					btnOK.setBounds(371, 448, 79, 25);
					btnOK.setName("btnOK");
					btnOK.setAction(getAppActionMap().get("Accept"));
				}
				
				fillUsers();
				panelCaseInformation.showData(new Project(), true);
			}
			this.setSize(575, 520);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	// Method used to show users
	private void fillUsers() {
		try {
			List<User> users = ClientController.getInstance().getUsers();
			DefaultListModel model = new DefaultListModel();
			int cont = 0;
			for(User u: users) {
				CheckableItem chkItem = new CheckableItem(u);
				model.add(cont, chkItem);
				cont++;
			}
			listUsers.setModel(model);
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
	
	@Action
	public void Accept() {
		// Validate data TODO
		// Create project and update users
		Project newProject = panelCaseInformation.getProject();
		try {
			ClientController.getInstance().createProject(newProject);
			this.dispose();
		} catch (RemoteException e) {
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
	
	@Action
	public void Cancel() {
		this.dispose();
	}

}
