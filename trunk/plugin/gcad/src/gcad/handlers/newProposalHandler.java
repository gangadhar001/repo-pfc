package gcad.handlers;

import gcad.internationalization.BundleInternationalization;
import gcad.wizards.AbstractNewProposalWizard;
import gcad.wizards.NewProposalMenuWizard;
import gcad.wizards.NewProposalMenuWizardPage;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

/** 
 * This class handles the "New Proposal" command from the "Knowledge" menu. 
 * It is used to show the new proposal wizard 
 */
public class newProposalHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractNewProposalWizard wizard = new NewProposalMenuWizard(BundleInternationalization.getString("NewProposalWizard"));
		wizard.addPages(new NewProposalMenuWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
	    dialog.create();
	    dialog.open();
		return null;

	}

}
