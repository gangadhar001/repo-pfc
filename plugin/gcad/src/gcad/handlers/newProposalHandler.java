package gcad.handlers;

/** 
 * TODO: Esta clase muestra el wizard para crear una nueva propuesta al seleccionar ese comando en el menu
 */
import gcad.internationalization.BundleInternationalization;
import gcad.wizards.AbstractNewProposalWizard;
import gcad.wizards.NewProposalMenuWizard;
import gcad.wizards.NewProposalMenuWizardPage;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class newProposalHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractNewProposalWizard wizard = new NewProposalMenuWizard(BundleInternationalization.getString("NewProposalWizard"));
		wizard.addPages(new NewProposalMenuWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
	    dialog.create();
	    dialog.open();
		return null;

	}

}
