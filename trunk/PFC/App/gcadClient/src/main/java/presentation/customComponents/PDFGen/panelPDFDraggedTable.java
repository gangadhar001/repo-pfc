package presentation.customComponents.PDFGen;
import internationalization.ApplicationInternationalization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.ActionMap;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import model.business.knowledge.PDFTable;
import model.business.knowledge.Project;
import model.business.knowledge.UserRole;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.JDConfigurePDFTable;
import presentation.customComponents.ImagePanel;
import presentation.panelsActions.panelPDFGeneration;
import resources.ImagesUtilities;
import bussiness.control.ClientController;
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
public class panelPDFDraggedTable extends panelPDFDragged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 908251059799542523L;
	private ImagePanel imagePanel;
	private Project project;
	private JButton btnConfigure;
	private JLabel lblWarning;
	private JLabel lblProjectName;
	private JButton btnDelete;
	private panelPDFGeneration parent;

	public panelPDFDraggedTable(panelPDFGeneration parent) {
		super();
		project = null;
		this.parent = parent;
		initGUI();
	}

	private void initGUI() {
		{
			this.setPreferredSize(new java.awt.Dimension(646, 67));
			this.setMaximumSize(new java.awt.Dimension(646, 67));
			this.setMinimumSize(new java.awt.Dimension(646, 67));
			this.setLayout(null);
			{
				imagePanel = new ImagePanel();
				try {
					imagePanel.setImage(ImagesUtilities.loadCompatibleImage("PDFElements/Table.png"));
				} catch (IOException e) { }
				this.add(imagePanel);
				imagePanel.setBounds(12, 12, 82, 71);
				imagePanel.setPreferredSize(new java.awt.Dimension(50, 45));
				imagePanel.setSize(50, 45);
				
			}
			{
				btnDelete = new JButton();
				this.add(btnDelete);
				btnDelete.setAction(getAppActionMap().get("Delete"));
				btnDelete.setName("btnDelete");
				try {
					btnDelete.setIcon(ImagesUtilities.loadIcon("menus/remove.png"));
				} catch (Exception e) { }
			}
			{
				btnDelete.setText("");
				btnDelete.setBounds(621, 5, 20, 15);
				btnDelete.setSize(20, 20);
			}
			lblProjectName = new JLabel();
			this.add(lblProjectName);
			lblProjectName.setBounds(98, 12, 505, 43);
			lblProjectName.setText(ApplicationInternationalization.getString("ProjectNameLabel"));
		}
		{
			lblWarning = new JLabel();
			this.add(lblWarning);
			lblWarning.setBounds(62, 1, 24, 24);
			lblWarning.setToolTipText(ApplicationInternationalization.getString("WarningTablePDF"));
			try {
				lblWarning.setIcon(ImagesUtilities.loadIcon("warning.png"));
			}
			catch (Exception e) { }
			if (!ClientController.getInstance().getRole().equals(UserRole.ChiefProject.name()))
				lblWarning.setVisible(false);
		}
		{
			btnConfigure = new JButton();
			this.add(btnConfigure);
			btnConfigure.setAction(getAppActionMap().get("Configure"));
			btnConfigure.setName("btnConfigure");
			btnConfigure.setBounds(65, 40, 22, 22);
			btnConfigure.setText("");
			btnConfigure.setContentAreaFilled(false);
			lblWarning.setToolTipText(ApplicationInternationalization.getString("ConfigureTablePDF"));
			try {
				btnConfigure.setIcon(ImagesUtilities.loadIcon("configure.png"));
			}
			catch (Exception e) { }
			// Only chief project role can select a project in order to generate the table
			if (!ClientController.getInstance().getRole().equals(UserRole.ChiefProject.name())){
				btnConfigure.setVisible(false);
			}
		}

		Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
	
	}
	
	// Show the dialog to choose a project 
	private void configurePDFTable() {
		JDConfigurePDFTable ct = new JDConfigurePDFTable();
		ct.setLocationRelativeTo(this);
		ct.setModal(true);
		ct.setVisible(true);
		// Get the selected project
		lblWarning.setVisible(false);
		btnConfigure.setEnabled(false);
		project = ct.getSelectedProject();
		lblProjectName.setText(ApplicationInternationalization.getString("ProjectNameLabel") + " " + project.getName());
	}

	public Project getProject() {
		try {
			project = ClientController.getInstance().getProjectsFromCurrentUser().get(0);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (NotLoggedException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		return project;
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	@Action
	public void Delete () {
		parent.removeDragged(this);
	}
	
	@Action
	public void Configure () {
		configurePDFTable();
	}

	// Method used to configure this panel, depends on the user role
	public void configureRole() throws RemoteException, NotLoggedException, Exception {
		// If user is employee, select, by default, the current project
		 if (ClientController.getInstance().getRole().equals(UserRole.Employee.name())) {
			 int index = ClientController.getInstance().getCurrentProject();
			 project = ClientController.getInstance().getProjectsFromCurrentUser().get(index);
			 lblProjectName.setText(ApplicationInternationalization.getString("ProjectNameLabel") + " " + project.getName());
		 }		
	}
	
}
