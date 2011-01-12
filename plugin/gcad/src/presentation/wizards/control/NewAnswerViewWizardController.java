package presentation.wizards.control;


import internationalization.BundleInternationalization;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

import model.business.control.Controller;
import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import presentation.wizards.NewAnswerViewWizardPage;

/**
 * This abstract class represents a New Answer Wizard when it is shown since the "Proposals" view
 */
public class NewAnswerViewWizardController extends AbstractNewKnowledgeWizardController {

	private Proposal proposalSelected;
	
	public NewAnswerViewWizardController(String wizardTitle, Proposal proposalSelected) {
		super(wizardTitle);
		this.proposalSelected = proposalSelected;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewAnswerViewWizardPage page = (NewAnswerViewWizardPage) super.getPage();
		final String nameText = page.getNameText();
		final String descriptionText = page.getDescriptionText();
		
		final Answer newAnswer = new Answer(nameText, descriptionText, new Date());
		// TODO: cambiar argumento

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newAnswer, proposalSelected);					
				} catch (SQLException e) {
					// It is only possible to throw this exception type
					throw new InvocationTargetException(e);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			Controller.getInstance().notifyKnowledgeAdded(newAnswer);
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
	 * The worker method. It will create and insert in the database a new proposal
	 */
	private void doFinish(IProgressMonitor monitor, Answer newAnswer, Proposal proposalSelected) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
		monitor.beginTask(BundleInternationalization.getString("ProposalMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ProposalMonitorMessage"));
		monitor.worked(10);
		// The new proposal is created and inserted into database
		Controller.getInstance().addAnwser(newAnswer, proposalSelected);
		monitor.worked(10);
		
	}
}
