package presentation.wizards.control.knowledge;

import internationalization.BundleInternationalization;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import model.business.control.Controller;
import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

/**
 * This abstract class represents a Wizard Controller to centralize behavior of the wizard pages related to add new knowledge
 */
public abstract class AbstractNewKnowledgeWC extends Wizard {
	
	private WizardPage page;
	
	public AbstractNewKnowledgeWC (String wizardTitle) {
		super();
		setWindowTitle(wizardTitle);
		setNeedsProgressMonitor(true);
		
	}
	
	public void addPages(WizardPage page) {
		super.addPages();
		this.page=page;
		addPage(page);
	}

	public WizardPage getPage() {
		return page;
	}
	
	/**
	 * This method is used to add a new proposal
	 */
	protected boolean runNewProposal(final Proposal newProposal, final Topic parentTopic) {
	
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newProposal, parentTopic);					
				} catch (SQLException e) {
					// It is only possible to throw this exception type, because of it's a new thread
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
			Controller.getInstance().notifyKnowledgeAdded(newProposal);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), BundleInternationalization.getString("Error"), realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * This method is used to add a new answer
	 */
	protected boolean runNewAnswer(final Answer newAnswer, final Proposal parentProposal) {
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newAnswer, parentProposal);					
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
			Controller.getInstance().notifyKnowledgeAdded(newAnswer);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), BundleInternationalization.getString("Error"), realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * This method is used to add a new answer
	 */
	protected boolean runNewTopic(final Topic topic) {
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, topic);					
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
			Controller.getInstance().notifyKnowledgeAdded(topic);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), BundleInternationalization.getString("Error"), realException.getMessage());
			return false;
		}
		return true;
	}
	
	
	/**
	 * The worker method. It will create and insert in the database a new proposal
	 */
	protected void doFinish(IProgressMonitor monitor, Proposal newProposal, Topic topicSelected) throws SQLException {		
		monitor.beginTask(BundleInternationalization.getString("ProposalMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ProposalMonitorMessage"));
		monitor.worked(10);
		// The new proposal is created and inserted into database
		Controller.getInstance().addProposal(newProposal, topicSelected);
		monitor.worked(10);
		
	}
	
	/**
	 * The worker method. It will create and insert in the database a new answer
	 */
	protected void doFinish(IProgressMonitor monitor, Answer newAnswer, Proposal proposalSelected) throws SQLException {		
		monitor.beginTask(BundleInternationalization.getString("AnswerMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("AnswerMonitorMessage"));
		monitor.worked(10);
		// The new answer is created and inserted into database
		Controller.getInstance().addAnwser(newAnswer, proposalSelected);
		monitor.worked(10);
	}
	
	/**
	 * The worker method. It will create and insert in the database a new topic
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
