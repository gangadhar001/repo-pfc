package presentation.wizards.control;


import internationalization.BundleInternationalization;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import model.business.control.Controller;
import model.business.knowledge.Topic;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;

import presentation.wizards.NewTopicWizardPage;

/**
 * This class allows to login
 */
public class NewTopicWizardController extends Wizard {
		
	private NewTopicWizardPage page;

	public NewTopicWizardController () {
		super();
		setWindowTitle(BundleInternationalization.getString("NewTopicWizard"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new NewTopicWizardPage(BundleInternationalization.getString("NewTopicWizardPageTitle"));
		addPage(page);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		String topicName = page.getTopicText();
		final Topic topic = new Topic(topicName);
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, topic);
					// At this point, it is only possible to throw the InvocationTargetException type			
				} catch (SQLException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			// Notify the the new topic added
			Controller.getInstance().notifyKnowledgeChanged();
			
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
	private void doFinish(IProgressMonitor monitor, Topic topic) throws SQLException {		
		monitor.beginTask(BundleInternationalization.getString("TopicMonitorMessage"), 50);

		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("TopicMonitorMessage"));
		monitor.worked(10);

		monitor.worked(10);
		Controller.getInstance().addTopic(topic);
	}
}
