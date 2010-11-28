package gcad.wizards;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

import gcad.domain.Categories;
import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.internationalization.BundleInternationalization;
import gcad.proposals.models.KnowledgeManager;
import gcad.views.ProposalView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * This abstract class represents a New Proposal Wizard
 */
public abstract class AbstractNewProposalWizard extends Wizard {

	private final static String PROPOSAL_VIEW_ID = "gcad.view.proposals";
	
	private WizardPage page;
	
	public AbstractNewProposalWizard (String wizardTitle) {
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
	
	protected boolean run(final String nameText, final String descriptionText, final String category, final Proposal parentProposal) {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, nameText, descriptionText, category, parentProposal);					
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
			// When a new proposal is added, the proposals view are refreshed
			boolean activeProposalsView = false;
			IViewReference[] references;
			for (IWorkbenchPage p: PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages()){
				references = p.getViewReferences();
				for (IViewReference reference : references)
					if (reference.getId().equals(PROPOSAL_VIEW_ID))
						activeProposalsView = true;
			}
			// If the proposals view is visible, it is updated
			if (activeProposalsView) {
				ProposalView pView = (ProposalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("gcad.view.proposals");
				pView.refresh();
			}
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * The worker method. It will create and insert in the database a new proposal
	 */
	private void doFinish(final IProgressMonitor monitor, final String name, final String description, final String category, Proposal parentProposal) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
		KnowledgeManager manager = KnowledgeManager.getManager();
		monitor.beginTask(BundleInternationalization.getString("ProposalMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ProposalMonitorMessage"));
		monitor.worked(10);
		// The new proposal is created and inserted into database
		Proposal newProposal = new Proposal(name, description, new Date(), Categories.valueOf(category), 0);
		monitor.worked(10);
		manager.addKnowledge(newProposal, parentProposal);
		monitor.worked(10);
		
	}
	
}
