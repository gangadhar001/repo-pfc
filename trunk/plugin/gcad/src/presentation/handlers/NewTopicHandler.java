package presentation.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.knowledge.NewTopicWC;

/** 
 * This class handles the "New Proposal" command from the "Knowledge" menu. 
 * It is used to show the new proposal wizard 
 */
public class NewTopicHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		NewTopicWC wizard = new NewTopicWC();
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
        dialog.create();
        dialog.open();
        return null;
	}

}
