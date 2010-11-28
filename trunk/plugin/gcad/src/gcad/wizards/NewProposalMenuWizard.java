package gcad.wizards;

import gcad.domain.Proposal;

/**
 * This abstract class represents a New Proposal Wizard when it is shown since the "Knowledge" menu
 */
public class NewProposalMenuWizard extends AbstractNewProposalWizard {

	public NewProposalMenuWizard(String wizardTitle) {
		super(wizardTitle);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewProposalMenuWizardPage page = (NewProposalMenuWizardPage) super.getPage();
		final String nameText = page.getNameText();
		final String descriptionText = page.getDescriptionText();
		final String category = page.getItemCategory();
		int indeParentProposal = page.getItemCbProposals();
		final Proposal parentProposal = (Proposal) page.getProposals().get(indeParentProposal);
		return super.run(nameText, descriptionText, category, parentProposal);
	}
}


