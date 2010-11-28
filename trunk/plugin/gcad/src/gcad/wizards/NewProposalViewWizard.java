package gcad.wizards;

import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.internationalization.BundleInternationalization;
import gcad.persistence.PFProposal;
import gcad.proposals.models.ProposalManager;
import gcad.views.ProposalView;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class NewProposalViewWizard extends AbstractNewProposalWizard {

	public NewProposalViewWizard(String wizardTitle) {
		super(wizardTitle);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewProposalViewWizardPage page = (NewProposalViewWizardPage) super.getPage();
		final String nameText = page.getNameText();
		final String descriptionText = page.getDescriptionText();
		final String category = page.getItemCategory();
		final Proposal parentProposal = ((ProposalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("gcad.view.proposals")).getProposalSelected();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(monitor, nameText, descriptionText, category, parentProposal);					
				} catch (SQLException e) {
					// TODO: solo se puede lanzar esta excepcion, por lo que se encapsula en ella
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
			//TODO: refrescar la vista de las propuestas
			boolean activeProposalsView = false;
			// Tomar las vistas de la perspectiva activa
			IViewReference[] references;
			for (IWorkbenchPage p: PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages()){
				references = p.getViewReferences();
				for (IViewReference reference : references)
					if (reference.getId().equals("gcad.view.proposals"))
						activeProposalsView = true;
			}
			// Si esta activa (visible),
			if (activeProposalsView) {
				ProposalView pView = (ProposalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("gcad.view.proposals");
				pView.refresh(parentProposal);
			}
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			//MessageDialog.openError(getShell(), "Error", BundleInternationalization.getString("ErrorMessage.SQLException"));
			//e.printStackTrace();
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
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoProjectProposalsException 
	 */

	private void doFinish(IProgressMonitor monitor, String name, String description, String category, Proposal parentProposal) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ProposalManager manager = ProposalManager.getManager();
		monitor.beginTask(BundleInternationalization.getString("ProposalMonitorMessage"), 50);
		
		monitor.worked(10);
		monitor.setTaskName(BundleInternationalization.getString("ProposalMonitorMessage"));
		monitor.worked(10);
		// TODO: se crea la nueva propuesta y se inserta en la base de datos
		Proposal newProposal = new Proposal(name, description, new Date(), 0);
		monitor.worked(10);
		manager.addKnowledge(newProposal, parentProposal);
		monitor.worked(10);
	}

}
