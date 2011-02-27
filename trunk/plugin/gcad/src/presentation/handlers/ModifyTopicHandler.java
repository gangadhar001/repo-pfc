package presentation.handlers;


import internationalization.BundleInternationalization;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.knowledge.AbstractModifyKnowledgeWC;
import presentation.wizards.control.knowledge.ModifyTopicMenuWC;
import presentation.wizards.knowledge.ModifyTopicWP;

/** 
 * This class handles the "Modify Topic" command from the "Knowledge" menu. 
 * It is used to show the modify Topic wizard 
 */
public class ModifyTopicHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractModifyKnowledgeWC wizard = new ModifyTopicMenuWC(BundleInternationalization.getString("ModifyTopicWizard"));
		wizard.addPages(new ModifyTopicWP(BundleInternationalization.getString("ModifyTopicWizardPageTitle"), null));
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
        dialog.create();
        dialog.open();
        return null;

	}

}
