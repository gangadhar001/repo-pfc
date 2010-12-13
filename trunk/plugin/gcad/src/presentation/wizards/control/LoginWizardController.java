package presentation.wizards.control;

import exceptions.IncorrectEmployeeException;

import internationalization.BundleInternationalization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import model.business.control.Controller;
import model.business.control.PresentationController;
import model.business.control.SessionController;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import persistence.communications.DBConfiguration;
import persistence.communications.DBConnectionManager;
import presentation.views.ProposalView;
import presentation.wizards.LoginWizardPage;

/**
 * This abstract class represents a DB Connection Wizard
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
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IncorrectEmployeeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} catch (SQLException e) {
					// It is only possible to throw this exception type
					throw new InvocationTargetException(e);
				
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			Controller.getInstance().notifyLogin();
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;	
		}
		return true;
	}
	
	/**
	 * The worker method. It will log in the user and create the database connection
	 */
	private void doFinish(IProgressMonitor monitor, String user, String pass, String IP, String port) throws CoreException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, IncorrectEmployeeException {		
		monitor.beginTask(BundleInternationalization.getString("DBMonitorMessage"), 60);
		// TODO: poner al usuario en una sesion
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("DBMonitorMessage"));

		// Create the configuration of the database connection
		DBConfiguration configuration = new DBConfiguration(IP, Integer.parseInt(port));
		monitor.worked(10);
		// Close older connections (ignore errors)
		try {
			DBConnectionManager.closeConnections();
			monitor.worked(10);
		} catch(SQLException e) { }
		DBConnectionManager.clearConnections();
		monitor.worked(10);
		
		// Create and initializate a new database connection
		DBConnectionManager.initializate(configuration);
		monitor.worked(10);
		
		// TODO: Identificarse
		Controller.getInstance().login(user,pass);		
	}
}
