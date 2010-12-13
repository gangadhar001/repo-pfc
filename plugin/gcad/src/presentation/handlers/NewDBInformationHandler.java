package presentation.handlers;


import java.util.Vector;

import model.business.knowledge.Operations;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import presentation.IPresentation;
import presentation.wizards.control.NewProjectWizardController;

public class NewDBInformationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		NewProjectWizardController wizard = new NewProjectWizardController();
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
	    dialog.create();
	    dialog.open();
		return null;
	}
}
