package presentation;
import internationalization.ApplicationInternationalization;

import java.rmi.RemoteException;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.customComponents.panelChooseProject;

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

public class JDChooseProject extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8757862422217062876L;
	private JButton btnOK;
	private JButton btnCancel;
	private panelChooseProject pnlChooseProject;

	public JDChooseProject(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
	
	private void initGUI() {
		try {
			{
				setUndecorated(true);
				getContentPane().setLayout(null);
				this.setTitle(ApplicationInternationalization.getString("chooseProjectTitle"));
				pnlChooseProject = new panelChooseProject();
				pnlChooseProject.setProjects(ClientController.getInstance().getProjectsFromCurrentUser());
				getContentPane().add(pnlChooseProject);
				pnlChooseProject.setBounds(0, 0, 322, 194);
			}
			{
				btnOK = new JButton();
				getContentPane().add(btnOK, "Center");
				btnOK.setBounds(138, 193, 81, 25);
				btnOK.setName("btnOK");
				btnOK.setAction(getAppActionMap().get("Ok"));
				btnOK.setText(ApplicationInternationalization.getString("btnOK"));
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setBounds(230, 193, 81, 25);
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setName("btnCancel");
				btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
			}
			this.setSize(338, 270);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Action
	public void Ok () {
		if (pnlChooseProject.getProjectId() != -1) {
			if (JOptionPane.showConfirmDialog(this, ApplicationInternationalization.getString("chooseProjectTitle"), ApplicationInternationalization.getString("l"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
					// TODO: hacer
					ClientController.getInstance().setCurrentProject(3);
					this.dispose();
					ClientController.getInstance().restartMainFrame();
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
				} catch (NotLoggedException e) {
					JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	@Action
	public void Cancel() {
		this.dispose();
	}
}
