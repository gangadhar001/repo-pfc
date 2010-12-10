package presentation.wizards.control;

import internationalization.BundleInternationalization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import exceptions.IncorrectEmployeeException;
import exceptions.IncorrectOptionException;
import exceptions.InvalidSessionException;

import model.business.control.ProjectController;
import model.business.control.SessionController;
import model.business.knowledge.Project;

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
import presentation.wizards.NewProjectWizardPage;

public class NewProjectWizard extends Wizard {

	private NewProjectWizardPage page;

	public NewProjectWizard () {
		super();
		setWindowTitle(BundleInternationalization.getString("NewProjectWizard"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new NewProjectWizardPage(BundleInternationalization.getString("NewProjectWizardPageTitle"));
		addPage(page);
	}
	
	@Override
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final Project project = new Project(page.getNameText(), page.getDescriptionText(), page.getStartDate(), page.getEndDate(), Double.parseDouble(page.getBudgetText()), Integer.parseInt(page.getQuantityLinesText()), page.getDomainText(), page.getProgLanguage(), Integer.parseInt(page.getEstimatedHoursText()));
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, project);					
				} catch (IncorrectOptionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidSessionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			// TODO: mensaje de confirmacion
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
	 * The worker method. It will create a new project and insert it into database
	 * @throws SQLException 
	 * @throws InvalidSessionException 
	 * @throws IncorrectOptionException 
	 */
	private void doFinish(IProgressMonitor monitor, Project project) throws IncorrectOptionException, InvalidSessionException, SQLException {		
		monitor.beginTask(BundleInternationalization.getString("ProjectMonitorMessage"), 60);
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ProjectMonitorMessage"));
		
		// TODO: recuperar id sesion
		// TODO: Crear e insertar el nuevo proyecto
		ProjectController.createProject(1, project);
		
		
	}


}
