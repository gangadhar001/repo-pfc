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
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import persistence.communications.DBConfiguration;
import presentation.wizards.LoginWizardPage;
import presentation.wizards.SelectCurrentProjectWizardPage;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;
import exceptions.NonPermissionRole;

/**
 * This class allows to login
 */
public class LoginWizardController extends Wizard {
		
	private LoginWizardPage page1;
	private SelectCurrentProjectWizardPage page2;

	public LoginWizardController () {
		super();
		setWindowTitle(BundleInternationalization.getString("LoginWizardTitle"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page1 = new LoginWizardPage(BundleInternationalization.getString("LoginWizardTitle"));
		addPage(page1);
		page2 = new SelectCurrentProjectWizardPage(BundleInternationalization.getString("SelectCurrentProjectWizardPageTitle"));
		addPage(page2);		
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final int selectedProject = page2.getItemCbProjects();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
					try {
						
						doFinish(monitor, selectedProject);					
					// At this point, it is only possible to throw the InvocationTargetException type
					/*} catch (InstantiationException e) {
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
					}	*/				
				
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			
			//InputDialogs a = new InputDialogs(PlatformUI.getWorkbench().getDisplay().getActiveShell());
			//a.open();
			
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
			MessageDialog.openError(getShell(), "Error", e.getLocalizedMessage());
		} catch (NonPermissionRole e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
		return true;
	}
		
	private void doFinish(IProgressMonitor monitor, int selectedProject) {
		monitor.beginTask(BundleInternationalization.getString("LoginMonitorMessage"), 60);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("LoginMonitorMessage"));
		
		Controller.getInstance().setCurrentProject(selectedProject);
		monitor.worked(10);
	}

	public LoginWizardPage getPage() {
		return page1;
	}
	
	public IWizardPage getNextPage() {
		return page2;
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		// TODO Auto-generated method stub
		return super.getNextPage(page);
	}
		
}
