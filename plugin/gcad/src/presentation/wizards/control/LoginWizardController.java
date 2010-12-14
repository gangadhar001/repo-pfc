package presentation.wizards.control;

import gcad.SourceProvider;
import internationalization.BundleInternationalization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Vector;

import model.business.control.Controller;
import model.business.knowledge.Operations;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.ISourceProviderService;

import persistence.communications.DBConfiguration;
import persistence.communications.DBConnectionManager;
import presentation.wizards.LoginWizardPage;
import exceptions.IncorrectEmployeeException;

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
			updateMenus();
			
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
	
	private void updateMenus() {
		//TODO: según el rol, activar/desactivar menus
		// Hacerlo por XML con DIGESTER
		ISourceProviderService sourceProviderService = (ISourceProviderService)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(ISourceProviderService.class);
		
		Vector<Operations> operations = Controller.getInstance().getAvailableOperations();
		if (!operations.contains(Operations.CreateProject)) {
			SourceProvider commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(SourceProvider.CONDITION_NEW_PROJECT);
			commandStateService.setNewProjectMenuItemVisible(false);
		}
		if (!operations.contains(Operations.AddProposal)) {
			SourceProvider commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(SourceProvider.CONDITION_NEW_PROPOSAL);
			commandStateService.setNewProposalMenuItemVisible(false);
		}
		if (!operations.contains(Operations.ModifyProposal)) {
			SourceProvider commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(SourceProvider.CONDITION_MODIFY_PROPOSAL);
			commandStateService.setModifyProposalMenuItemVisible(false);
		}
		if (!operations.contains(Operations.DeleteProposal)) {
			SourceProvider commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(SourceProvider.CONDITION_DELETE_PROPOSAL);
			commandStateService.setDeleteProposalMenuItemVisible(false);
		}
		if (!operations.contains(Operations.AddAnswer)) {
			SourceProvider commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(SourceProvider.CONDITION_NEW_ANSWER);
			commandStateService.setNewAnswerMenuItemVisible(false);
		}
		if (!operations.contains(Operations.ModifyAnswer)) {
			SourceProvider commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(SourceProvider.CONDITION_MODIFY_ANSWER);
			commandStateService.setModifyAnswerMenuItemVisible(false);
		}	
		if (!operations.contains(Operations.DeleteAnswer)) {
			SourceProvider commandStateService = (SourceProvider) sourceProviderService.getSourceProvider(SourceProvider.CONDITION_DELETE_ANSWER);
			commandStateService.setDeleteAnswerMenuItemVisible(false);
		}
	}
}
