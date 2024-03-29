package presentation;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import internationalization.ApplicationInternationalization;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import bussiness.control.ClientController;

import presentation.customComponents.panelChooseProject;
import resources.ImagesUtilities;

public class JDConfigurePDFTable extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3477751154937839587L;
	private panelChooseProject pnlChooseProject;
	private JButton btnOK;
	private JButton btnCancel;
	private Project selectedProject = null;

	public JDConfigurePDFTable() {
		super();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
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

	public void initGUI() {
		try {
			setIconImage(ImagesUtilities.loadCompatibleImage("icono.png"));
		} catch (IOException e1) { }
		
		try {
			{
				setResizable(false);
				getContentPane().setLayout(null);
				this.setTitle(ApplicationInternationalization
						.getString("chooseProjectTitle"));
				pnlChooseProject = new panelChooseProject();
				pnlChooseProject.setProjects(ClientController.getInstance()
						.getProjectsFromCurrentUser());
				getContentPane().add(pnlChooseProject);
				pnlChooseProject.setBounds(0, 0, 322, 95);
			}
			{
				btnOK = new JButton();
				getContentPane().add(btnOK, "Center");
				btnOK.setBounds(132, 123, 81, 25);
				btnOK.setName("btnOK");
				btnOK.setAction(getAppActionMap().get("Ok"));
				btnOK.setText(ApplicationInternationalization
						.getString("btnOK"));
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setBounds(218, 123, 81, 25);
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setName("btnCancel");
				btnCancel.setText(ApplicationInternationalization
						.getString("CancelButton"));
			}
			this.setSize(330, 198);
			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(),
					ApplicationInternationalization.getString("Error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Action
	public void Ok() {
		if (pnlChooseProject.getProjectId() != -1) {
			selectedProject = pnlChooseProject.getProject();		
			this.dispose();
		}
	}

	@Action
	public void Cancel() {
		this.dispose();
	}

	public Project getSelectedProject() {
		return selectedProject;
	}
	
}
