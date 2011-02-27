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
 *  This abstract class represents a Wizard Controller to centralize behavior of the wizard pages related to modify knowledge  
 */
public abstract class AbstractModifyKnowledgeWC extends Wizard {
	
	private WizardPage page;
	
	public AbstractModifyKnowledgeWC (String wizardTitle) {
		super();
		setWindowTitle(wizardTitle);
		setNeedsProgressMonitor(true);
		
	}
	
	public void addPages(WizardPage page) {
		super.addPages();
		this.page=page;
		addPage(page);
	}

	/**
	 * This method is used to modify a proposal
	 */
	protected boolean runModifyProposal(final Proposal newProposal, final Proposal oldProposal, final Topic parentTopic) {
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newProposal, oldProposal, parentTopic);					
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
			Controller.getInstance().notifyKnowledgeChanged(newProposal);
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
	 * This method is used to modify an answer
	 */
	protected boolean runModifyAnswer(final Answer newAnswer, final Answer oldAnswer, final Proposal parentProposal) {
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newAnswer, oldAnswer, parentProposal);					
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
			Controller.getInstance().notifyKnowledgeChanged(newAnswer);
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
	 * This method is used to modify a topic
	 */
	protected boolean runModifyTopic(final Topic newTopic, final Topic oldTopic) {
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newTopic, oldTopic);					
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
			Controller.getInstance().notifyKnowledgeChanged(newTopic);
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
	 * The worker method. It will modify a proposal
	 */
	protected void doFinish(IProgressMonitor monitor, Proposal newProposal, Proposal oldProposal, Topic topicSelected) throws SQLException {		
		monitor.beginTask(BundleInternationalization.getString("ModifyProposalMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ModifyProposalMonitorMessage"));
		monitor.worked(10);

		Controller.getInstance().modifyProposal(newProposal, oldProposal, topicSelected);
		monitor.worked(10);
		
	}
	
	/**
	 * The worker method. It will modify an answer
	 */
	protected void doFinish(IProgressMonitor monitor, Answer newAnswer, Answer oldAnswer, Proposal proposalSelected) throws SQLException {		
		monitor.beginTask(BundleInternationalization.getString("ModifyAnswerMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ModifyAnswerMonitorMessage"));
		monitor.worked(10);

		Controller.getInstance().modifyAnswer(newAnswer, oldAnswer, proposalSelected);
		monitor.worked(10);
		
	}
	
	/**
	 * The worker method. It will modify a topic
	 */
	protected void doFinish(IProgressMonitor monitor, Topic newTopic, Topic oldTopic) throws SQLException {		
		monitor.beginTask(BundleInternationalization.getString("ModifyAnswerMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ModifyAnswerMonitorMessage"));
		monitor.worked(10);

		Controller.getInstance().modifyTopic(newTopic, oldTopic);
		monitor.worked(10);
		
	}
	
	public WizardPage getPage() {
		return page;
	}
		
}
