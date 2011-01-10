package presentation.wizards.control;


import java.util.Date;

import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import presentation.wizards.NewProposalViewWizardPage;

/**
 * This abstract class represents a New Proposal Wizard when it is shown since the "Proposals" view
 */
public class NewProposalViewWizardController extends AbstractNewKnowledgeWizardController {

	private Proposal proposalSelected;
	public NewProposalViewWizardController(String wizardTitle, Proposal proposalSelected) {
		super(wizardTitle);
		this.proposalSelected = proposalSelected;
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
		// TODO: cambiar estado;
		Proposal newProposal = new Proposal(nameText, descriptionText, new Date(), Categories.valueOf(category), 0);
		return super.run(newProposal, proposalSelected);
	}
}
