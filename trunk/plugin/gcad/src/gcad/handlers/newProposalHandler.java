package gcad.handlers;

import gcad.internationalization.BundleInternationalization;
import gcad.wizards.AbstractNewProposalWizard;
import gcad.wizards.NewProposalViewWizard;
import gcad.wizards.NewProposalViewWizardPage;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class newProposalHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AbstractNewProposalWizard wizard = new NewProposalViewWizard(BundleInternationalization.getString("NewProposalWizard"));
		wizard.addPages(new NewProposalViewWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
	    dialog.create();
	    dialog.open();
		return null;

	}

}
