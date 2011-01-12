package presentation.wizards.control;


import internationalization.BundleInternationalization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import model.business.control.Controller;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;

import persistence.communications.DBConfiguration;
import presentation.wizards.LoginWizardPage;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;

/**
 * This class allows to login
 */
public class LoginWizardController extends Wizard {
		
	private LoginWizardPage page;

	public LoginWizardController () {
		super();
		setWindowTitle(BundleInternationalization.getString("LoginWizardTitle"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new LoginWizardPage(BundleInternationalization.getString("LoginWizardTitle"));
		addPage(page);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String userName = page.getLoginText();
		final String passText = page.getPassText();
		final String IPText = page.getIPText();
		final String portText = page.getPortText();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					try {
						doFinish(monitor, userName, passText, IPText, portText);
					// At this point, it is only possible to throw the InvocationTargetException type
					} catch (InstantiationException e) {
						throw new InvocationTargetException(e);
					} catch (IllegalAccessException e) {
						throw new InvocationTargetException(e);
					} catch (ClassNotFoundException e) {
						throw new InvocationTargetException(e);
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					} catch (IncorrectEmployeeException e) {
						throw new InvocationTargetException(e);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					} catch (SQLException e) {
						throw new InvocationTargetException(e);
					} catch (NonExistentRole e) {
						throw new InvocationTargetException(e);
					}					
				
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			// Notify the actions that the user logged can do
			Controller.getInstance().notifyActionAllowed();
			// Notify to the views the satisfactory login
			Controller.getInstance().notifyLogin();			
			
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		} catch (ConfigurationException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		} catch (NonPermissionRole e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
		return true;
	}
	
	/**
	 * The worker method. It will log in the user and create the database connection
	 */
	private void doFinish(IProgressMonitor monitor, String user, String pass, String IP, String port) throws CoreException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, IncorrectEmployeeException, NonExistentRole {		
		monitor.beginTask(BundleInternationalization.getString("LoginMonitorMessage"), 60);

		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("LoginMonitorMessage"));

		// Create the configuration of the database connection
		DBConfiguration configuration = new DBConfiguration(IP, Integer.parseInt(port));
		monitor.worked(10);
		
		// Create and initialize a new database connection
		Controller.getInstance().initDBConnection(configuration);
		monitor.worked(10);
		
		// Login
		Controller.getInstance().login(user,pass);		
	}
}
