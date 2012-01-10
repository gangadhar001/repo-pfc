package presentation.CBR;

import internationalization.ApplicationInternationalization;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import model.business.control.CBR.CaseEval;
import model.business.knowledge.KnowledgeStatus;
import model.business.knowledge.PDFConfiguration;
import model.business.knowledge.PDFElement;
import model.business.knowledge.PDFSection;
import model.business.knowledge.PDFTable;
import model.business.knowledge.PDFText;
import model.business.knowledge.PDFTitle;
import model.business.knowledge.Project;
import model.business.knowledge.User;

import org.japura.gui.ArrowButton;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.JDPdf;
import presentation.JFMain;
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
public class JDRetrievalCases extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5940879165571381847L;
	private panelProjectInformation currentPanel;
	private ArrowButton btnBackward;
	private ArrowButton btnForward;
	private JLabel lblNumberCases;	
	private List<CaseEval> cases;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JLabel lblSimilarity;
	private JButton btnExport;
	private JButton btnSave;
	private JToolBar toolbar;
	private JPanel pnlProject;
	private JLabel lblUserInfo;
	private JList listUsers;
	private txtUserInformation txtUserInfo;
	private JPanel pnlUsersCreate;
	private JButton btnOk;
	private int currentProject;
	private panelKnowledgeTree panelTree;
	private DefaultListModel model;
	private JFMain parentD;
	
	public JDRetrievalCases(List<CaseEval> cases, JFMain jfMain) {
		super();
		this.cases = cases;
		this.parentD = jfMain;
		setTitle(ApplicationInternationalization.getString("retrievalCases"));
		currentProject = 1;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
                  closeWin();
			}
         });
		
		initGUI();
		// Show information about the case (project)
		showCaseInformation();
	}

	protected void closeWin() {
		this.dispose();
		
	}
	
	// Method used to show information about the case (project)
	private void showCaseInformation() {
		txtUserInfo.setText("");
		currentPanel.showData(cases.get(currentProject-1).getCaseP(), false, false, 180);
		fillUsers();
		showUsers(cases.get(currentProject-1).getCaseP());
		String sim = String.valueOf(cases.get(currentProject-1).getEval() * 100.0);
		if (sim.equals("NaN"))
			sim = "0.00";	
		lblSimilarity.setText(ApplicationInternationalization.getString("lblSimilarity") + " " + String.valueOf(sim) + "%");
		try {
			panelTree.showTree(ClientController.getInstance().getTopicsWrapper(cases.get(currentProject-1).getCaseP()), KnowledgeStatus.All);
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
			setIconImage(ImagesUtilities.loadCompatibleImage("icono.png"));
		} catch (IOException e1) { }
		
		try {
			{
				setResizable(false);
				getContentPane().setLayout(null);
				{
					lblNumberCases = new JLabel();
					getContentPane().add(lblNumberCases);
					lblNumberCases.setBounds(7, 609, 111, 26);
					lblNumberCases.setName("lblNumberCases");					
					lblNumberCases.setText(ApplicationInternationalization.getString("lblNumberCases") + " " + currentProject+"/"+cases.size());
				}
				{
					btnForward = new ArrowButton(ArrowButton.DOUBLE_RIGHT);
					getContentPane().add(btnForward);
					btnForward.setBounds(286, 617, 24, 17);
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
					btnBackward.setBounds(257, 618, 23, 14);
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
					btnOk.setBounds(827, 608, 82, 27);
					btnOk.setName("btnOk");
					btnOk.setAction(getAppActionMap().get("OK"));
					btnOk.setText(ApplicationInternationalization.getString("btnOK"));
				}
				{
					panelTree = new panelKnowledgeTree();
					getContentPane().add(panelTree);
					panelTree.setBounds(623, 46, 283, 546);
					panelTree.setSize(286, 550);
				}
				{
					pnlProject = new JPanel();
					getContentPane().add(pnlProject);
					pnlProject.setBounds(4, 46, 613, 548);
					pnlProject.setLayout(null);
					{
						currentPanel = new panelProjectInformation();
						pnlProject.add(currentPanel);
						currentPanel.setBounds(0, 0, 357, 548);
					}
					{
						pnlUsersCreate = new JPanel();
						pnlProject.add(pnlUsersCreate);
						pnlUsersCreate.setBorder(BorderFactory.createTitledBorder(ApplicationInternationalization.getString("panelUsers")));
						pnlUsersCreate.setLayout(null);
						pnlUsersCreate.setBounds(363, 0, 243, 548);
						{
							jScrollPane2 = new JScrollPane();
							pnlUsersCreate.add(jScrollPane2);
							jScrollPane2.setBounds(12, 369, 222, 163);
							{
								txtUserInfo = new txtUserInformation();
								jScrollPane2.setViewportView(txtUserInfo);
								txtUserInfo.setPreferredSize(new java.awt.Dimension(219, 146));
								txtUserInfo.setBounds(17, 306, 220, 92);
								txtUserInfo.setName("txtUserInfo");
							}
						}
						{
							jScrollPane1 = new JScrollPane();
							pnlUsersCreate.add(jScrollPane1);
							jScrollPane1.setBounds(12, 21, 222, 309);
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
										txtUserInfo.showUserInfo(item.getUser());
									}
								});
							}
						}
						{
							lblUserInfo = new JLabel();
							pnlUsersCreate.add(lblUserInfo);
							lblUserInfo.setName("lblUserInfo");
							lblUserInfo.setBounds(12, 342, 110, 13);
							lblUserInfo.setText(ApplicationInternationalization.getString("lblUserInfo"));
						}
					}
				}
				{
					toolbar = new JToolBar();
					getContentPane().add(toolbar);
					toolbar.setBounds(0, 0, 913, 40);
					{
						btnSave = new JButton();
						toolbar.add(btnSave);
						btnSave.setPreferredSize(new java.awt.Dimension(20, 9));
						btnSave.setSize(28, 28);
						setToolbarButtonProperties(btnSave, "SaveAsPDF");
					}
					{
						btnExport = new JButton();
						toolbar.add(btnExport);
						btnExport.setPreferredSize(new java.awt.Dimension(20, 9));
						btnExport.setSize(28, 28);
						setToolbarButtonProperties(btnExport, "ExportXML");
					}
				}
				{
					lblSimilarity = new JLabel();
					getContentPane().add(lblSimilarity);
					lblSimilarity.setBounds(119, 613, 126, 18);
					lblSimilarity.setFont(new Font(btnSave.getFont().getName(), Font.BOLD, btnSave.getFont().getSize()));
					lblSimilarity.setText(ApplicationInternationalization.getString("lblSimilarity"));
				}
				{
					
				}
			}
			this.setSize(929, 674);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
			getRootPane().setDefaultButton(btnOk);
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
	
	@Action
	public void SaveAsPDF() {
		 CursorUtilities.showWaitCursor(this);
		 // Create PDF sections
		 List<PDFSection> sections = new ArrayList<PDFSection>();
		 List<PDFElement> elements = new ArrayList<PDFElement>();
		 Project p = cases.get(currentProject-1).getCaseP();
		 PDFTitle title = new PDFTitle("Decisions for the project: " + p.getName());
		 PDFText text = new PDFText("The following table summarizes the decisions taken in the project: " + p.getName());
		 PDFTable table = new PDFTable(p);
		 elements.add(title);
		 elements.add(text);
		 elements.add(table);
		 PDFSection section = new PDFSection(elements);
		 sections.add(section);
		 
		 PDFConfiguration config = new PDFConfiguration(sections);
		 if (config.isValid()) {
			 // Show dialog in order to compose the PDF document
			 JDPdf dialog = new JDPdf(config);
			 dialog.setLocationRelativeTo(this);
			 dialog.setModal(true);
			 dialog.setVisible(true);
			 CursorUtilities.showDefaultCursor(this);
		 }
		 else {
			 CursorUtilities.showDefaultCursor(this);
			 JOptionPane.showMessageDialog(this, config.getErrorMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		 }
	}
	
	@Action
	public void ExportXML() {
		parentD.export(cases.get(currentProject-1).getCaseP());
	}
}
