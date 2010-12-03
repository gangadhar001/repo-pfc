package gcad.handlers;

import gcad.wizards.NewProjectWizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class newDBInformationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		NewProjectWizard wizard = new NewProjectWizard();
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
	    dialog.create();
	    dialog.open();
		return null;

	}

}
