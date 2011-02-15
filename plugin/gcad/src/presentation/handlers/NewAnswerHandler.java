package presentation.handlers;


import internationalization.BundleInternationalization;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.knowledge.AbstractNewKnowledgeWC;
import presentation.wizards.control.knowledge.NewAnswerMenuWC;
import presentation.wizards.knowledge.NewAnswerMenuWP;

/** 
 * This class handles the "New Proposal" command from the "Knowledge" menu. 
 * It is used to show the new proposal wizard 
 */
public class NewAnswerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractNewKnowledgeWC wizard = new NewAnswerMenuWC(BundleInternationalization.getString("NewAnswerWizard"));
		wizard.addPages(new NewAnswerMenuWP(BundleInternationalization.getString("NewAnswerWizardPageTitle")));
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
        dialog.create();
        dialog.open();
        return null;

	}

}
