package presentation.wizards.control;

import internationalization.BundleInternationalization;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import model.business.control.ProjectController;
import model.business.knowledge.Project;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;

import presentation.wizards.NewProjectWizardPage;

public class NewProjectWizardController extends Wizard {

	private NewProjectWizardPage page;

	public NewProjectWizardController () {
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
				} catch (SQLException e) {
					throw new InvocationTargetException(e);
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
	 */
	private void doFinish(IProgressMonitor monitor, Project project) throws SQLException {		
		monitor.beginTask(BundleInternationalization.getString("ProjectMonitorMessage"), 60);
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ProjectMonitorMessage"));
		
		// TODO: Crear e insertar el nuevo proyecto
		ProjectController.createProject(project);
		
		
	}


}
