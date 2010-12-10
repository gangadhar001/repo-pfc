package presentation.handlers;


import internationalization.BundleInternationalization;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.NewProposalMenuWizardPage;
import presentation.wizards.control.AbstractNewProposalWizardController;
import presentation.wizards.control.NewProposalMenuWizard;

/** 
 * This class handles the "New Proposal" command from the "Knowledge" menu. 
 * It is used to show the new proposal wizard 
 */
public class NewProposalHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractNewProposalWizardController wizard = new NewProposalMenuWizard(BundleInternationalization.getString("NewProposalWizard"));
		wizard.addPages(new NewProposalMenuWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
        dialog.create();
        dialog.open();
        return null;

	}

}
