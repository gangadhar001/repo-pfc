package presentation;
import internationalization.ApplicationInternationalization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

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
	
	private void initGUI() {
		try {
			{
				this.setIconImage(null);
				getContentPane().setLayout(null);
				this.setResizable(false);
				this.setTitle(ApplicationInternationalization.getString("chooseProjectTitle"));
				pnlChooseProject = new panelChooseProject();
				pnlChooseProject.setProjects(ClientController.getInstance().getProjectsFromCurrentUser());
				getContentPane().add(pnlChooseProject);
				pnlChooseProject.setBounds(0, 0, 322, 95);
			}
			{
				btnOK = new JButton();
				getContentPane().add(btnOK, "Center");
				btnOK.setBounds(132, 123, 81, 25);
				btnOK.setName("btnOK");
				btnOK.setAction(getAppActionMap().get("Ok"));
				btnOK.setText(ApplicationInternationalization.getString("btnOK"));
			}
			{
				btnCancel = new JButton();
				getContentPane().add(btnCancel);
				btnCancel.setBounds(218, 123, 81, 25);
				btnCancel.setAction(getAppActionMap().get("Cancel"));
				btnCancel.setName("btnCancel");
				btnCancel.setText(ApplicationInternationalization.getString("CancelButton"));
			}
			this.setSize(330, 198);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Action
	public void Ok () {
		if (pnlChooseProject.getProjectId() != -1) {
			try {
				if (pnlChooseProject.getProjectId() != ClientController.getInstance().getCurrentProject()) {
					if (JOptionPane.showConfirmDialog(this, ApplicationInternationalization.getString("Dialog_SwitchProject_Message"), ApplicationInternationalization.getString("chooseProjectTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {				
						ClientController.getInstance().setCurrentProject(pnlChooseProject.getProjectId());
						this.dispose();
						ClientController.getInstance().clearMainFrame();
						ClientController.getInstance().setCurrentProjectName(pnlChooseProject.getProject().getName());
					}
				}
				else {
					JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("sameProjectChange"), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
				}
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Action
	public void Cancel() {
		this.dispose();
	}
}
