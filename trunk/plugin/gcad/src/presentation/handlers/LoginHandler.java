package presentation.handlers;



import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.wizards.control.LoginWizardController;

public class LoginHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		LoginWizardController wizard = new LoginWizardController();
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
	    dialog.create();
	    dialog.open();
		return null;

	}


}
