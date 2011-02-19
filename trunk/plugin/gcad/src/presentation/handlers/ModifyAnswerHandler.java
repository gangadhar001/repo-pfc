package presentation.handlers;


import internationalization.BundleInternationalization;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.knowledge.AbstractModifyKnowledgeWC;
import presentation.wizards.control.knowledge.ModifyAnswerMenuWC;
import presentation.wizards.knowledge.ModifyAnswerMenuWP;

/** 
 * This class handles the "New Proposal" command from the "Knowledge" menu. 
 * It is used to show the new proposal wizard 
 */
public class ModifyAnswerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractModifyKnowledgeWC wizard = new ModifyAnswerMenuWC(BundleInternationalization.getString("NewProposalWizard"));
		wizard.addPages(new ModifyAnswerMenuWP(BundleInternationalization.getString("NewProposalWizardPageTitle")));
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
        dialog.create();
        dialog.open();
        return null;

	}

}
