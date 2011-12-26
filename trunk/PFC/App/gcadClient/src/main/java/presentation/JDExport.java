package presentation;
import internationalization.ApplicationInternationalization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import model.business.knowledge.Project;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jfree.ui.ExtensionFileFilter;

import presentation.customComponents.panelChooseProject;
import resources.CursorUtilities;

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

public class JDExport extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8757862422217062876L;
	private JButton btnOK;
	private JButton btnCancel;
	private panelChooseProject pnlChooseProject;

	public JDExport(JFrame frame) {
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
		byte[] baos;
		java.io.File f;
		if (pnlChooseProject.getProjectId() != -1) {
			Project p = pnlChooseProject.getProject();
			try {
				JFileChooser fc = new JFileChooser();
				ExtensionFileFilter filter = new ExtensionFileFilter("XML File", "XML");
				fc.setFileFilter(filter);
				fc.showSaveDialog(this);
				if ((f = fc.getSelectedFile()) != null)  {
					CursorUtilities.showWaitCursor(this);
					baos = ClientController.getInstance().exportInformation(p);		
					if (baos != null) {
						String path = f.getAbsolutePath();
						if (!path.endsWith("."))
							path += ".xml";
				        FileOutputStream fos = new FileOutputStream(path);
				        fos.write(baos);
				        CursorUtilities.showDefaultCursor(this);
				        //showStatusBar(ApplicationInternationalization.getString("exportStatus"));				        
				        JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("exportSuccessfully"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(this, ApplicationInternationalization.getString("informationExport"), ApplicationInternationalization.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (JAXBException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NonPermissionRoleException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (NotLoggedException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException e) {
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
