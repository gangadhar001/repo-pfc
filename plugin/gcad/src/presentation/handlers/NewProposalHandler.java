package presentation.handlers;


import internationalization.BundleInternationalization;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.knowledge.AbstractNewKnowledgeWC;
import presentation.wizards.control.knowledge.NewProposalMenuWC;
import presentation.wizards.knowledge.NewProposalMenuWP;

/** 
 * This class handles the "New Proposal" command from the "Knowledge" menu. 
 * It is used to show the new proposal wizard 
 */
public class NewProposalHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractNewKnowledgeWC wizard = new NewProposalMenuWC(BundleInternationalization.getString("NewProposalWizard"));
		wizard.addPages(new NewProposalMenuWP(BundleInternationalization.getString("NewProposalWizardPageTitle")));
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
        dialog.create();
        dialog.open();
        return null;

	}

}
