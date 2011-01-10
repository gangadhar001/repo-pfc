package presentation.wizards.control;

import internationalization.BundleInternationalization;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import model.business.control.Controller;
import model.business.knowledge.AbstractProposal;
import model.business.knowledge.Proposal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import exceptions.NoProjectProposalsException;

/**
 * This abstract class represents a New Proposal Wizard
 */
public abstract class AbstractNewKnowledgeWizardController extends Wizard {
	
	private WizardPage page;
	
	public AbstractNewKnowledgeWizardController (String wizardTitle) {
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
	
	protected boolean run(final AbstractProposal newKnowledge, final Proposal parentProposal) {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, newKnowledge, parentProposal);					
				} catch (SQLException e) {
					// It is only possible to throw this exception type
					throw new InvocationTargetException(e);
				} catch (NoProjectProposalsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			Controller.getInstance().notifyKnowledgeAdded();
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		} /*catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		return true;
	}
	
	/**
	 * The worker method. It will create and insert in the database a new proposal
	 */
	private void doFinish(IProgressMonitor monitor, AbstractProposal newKnowledge, Proposal parentProposal) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
		monitor.beginTask(BundleInternationalization.getString("ProposalMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ProposalMonitorMessage"));
		monitor.worked(10);
		// The new proposal is created and inserted into database
		Controller.getInstance().addKnowledge(newKnowledge, parentProposal);
		monitor.worked(10);
		
	}
	
}
