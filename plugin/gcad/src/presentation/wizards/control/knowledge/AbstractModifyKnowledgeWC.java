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

import exceptions.IncorrectEmployeeException;

/**
 * This abstract class represents a New Proposal Wizard
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

	protected boolean runNewProposal(final Proposal newProposal, final Topic parentTopic) {
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newProposal, parentTopic);					
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
				} catch (IncorrectEmployeeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	protected boolean runModifyAnswer(final Answer answer, final Proposal parentProposal) {
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, answer, parentProposal);					
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
			//Controller.getInstance().notifyKnowledgeAdded(newAnswer);
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
	protected void doFinish(IProgressMonitor monitor, Proposal newProposal, Topic topicSelected) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IncorrectEmployeeException {		
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
	protected void doFinish(IProgressMonitor monitor, Answer answer, Proposal proposalSelected) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
		monitor.beginTask(BundleInternationalization.getString("AnswerMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("AnswerMonitorMessage"));
		monitor.worked(10);
		// The new proposal is created and inserted into database
		Controller.getInstance().updateAnswer(answer, proposalSelected);
		monitor.worked(10);
		
	}
	
	public WizardPage getPage() {
		return page;
	}
		
}
