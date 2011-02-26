package presentation.handlers;


import internationalization.BundleInternationalization;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.knowledge.AbstractModifyKnowledgeWC;
import presentation.wizards.control.knowledge.ModifyProposalMenuWC;
import presentation.wizards.knowledge.ModifyProposalMenuWP;

/** 
 * This class handles the "Modify Proposal" command from the "Knowledge" menu. 
 * It is used to show the modify Proposal wizard 
 */
public class ModifyProposalHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractModifyKnowledgeWC wizard = new ModifyProposalMenuWC(BundleInternationalization.getString("ModifyProposalWizard"));
		wizard.addPages(new ModifyProposalMenuWP(BundleInternationalization.getString("ModifyProposalWizardPageTitle"), null));
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
        dialog.create();
        dialog.open();
        return null;

	}

}
