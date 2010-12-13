package presentation.wizards.control;


import model.business.knowledge.Proposal;

import org.eclipse.ui.PlatformUI;

import presentation.views.ProposalView;
import presentation.wizards.NewProposalViewWizardPage;

/**
 * This abstract class represents a New Proposal Wizard when it is shown since the "Proposals" view
 */
public class NewProposalViewWizardController extends AbstractNewProposalWizardController {

	public NewProposalViewWizardController(String wizardTitle) {
		super(wizardTitle);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewProposalViewWizardPage page = (NewProposalViewWizardPage) super.getPage();
		final String nameText = page.getNameText();
		final String descriptionText = page.getDescriptionText();
		final String category = page.getItemCategory();
		final Proposal parentProposal = ((ProposalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("gcad.view.proposals")).getProposalSelected();
		return super.run(nameText, descriptionText, category, parentProposal);
	}
}
