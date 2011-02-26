package presentation.utils;

import java.io.IOException;
import java.sql.SQLException;

import model.business.control.Controller;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import presentation.wizards.control.login.LoginWizardController;
import presentation.wizards.login.LoginWizardPage;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

public class LoginWizardDialog extends WizardDialog {

	private IWizard newWizard;
	
	public LoginWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		this.newWizard = newWizard;
	}
	
	// When push "Next" button, connect to database and show next page
	@Override
	protected void nextPressed() {		
		if (newWizard instanceof LoginWizardController) {
			LoginWizardPage page = ((LoginWizardController) newWizard).getPage();

			String userName = page.getLoginText();
			String passText = page.getPassText();
			String IPText = page.getIPText();
			String portText = page.getPortText();
			try {
				doNext(userName, passText, IPText, portText);
				super.nextPressed();
			} catch (CoreException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			} catch (SQLException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			} catch (InstantiationException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			} catch (IllegalAccessException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			} catch (ClassNotFoundException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			} catch (IOException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			} catch (IncorrectEmployeeException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			} catch (NonExistentRole e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			}
		}
	}
	
	/**
	 * This will create the database connection and log in the user
	 */
	private void doNext(String user, String pass, String IP, String port) throws CoreException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, IncorrectEmployeeException, NonExistentRole {				
		// Create and initialize a new database connection
		Controller.getInstance().initDBConnection(IP, port);		
		// Login
		Controller.getInstance().login(user,pass);		
	}

}
