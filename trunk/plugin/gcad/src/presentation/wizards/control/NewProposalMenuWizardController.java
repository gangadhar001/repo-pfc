package presentation.wizards.control;

import java.util.Date;

import presentation.wizards.NewProposalMenuWizardPage;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;

/**
 * This abstract class represents a New Proposal Wizard when it is shown since the "Knowledge" menu
 */
public class NewProposalMenuWizardController extends AbstractNewKnowledgeWizardController {

	public NewProposalMenuWizardController(String wizardTitle) {
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
		Proposal newProposal = new Proposal(nameText, descriptionText, new Date(), Categories.valueOf(category), 0);
		return super.run(newProposal, parentProposal);
	}
}


