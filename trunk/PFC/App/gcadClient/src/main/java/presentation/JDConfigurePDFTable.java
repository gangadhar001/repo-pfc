package presentation;

import internationalization.ApplicationInternationalization;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import bussiness.control.ClientController;

import presentation.customComponents.panelChooseProject;

public class JDConfigurePDFTable extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3477751154937839587L;
	private panelChooseProject pnlChooseProject;
	private JButton btnOK;
	private JButton btnCancel;
	private Project selectedProject;

	public JDConfigurePDFTable() {
		super();
		initGUI();
	}

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}

	public void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				this.setResizable(false);
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
