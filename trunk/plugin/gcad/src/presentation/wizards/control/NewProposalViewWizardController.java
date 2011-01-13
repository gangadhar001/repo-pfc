package presentation.wizards.control;


import java.util.Date;

import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import presentation.wizards.NewProposalViewWizardPage;

/**
 * This abstract class represents a New Proposal Wizard when it is shown since the "Proposals" view
 */
public class NewProposalViewWizardController extends AbstractNewKnowledgeWizardController {

	private Topic topicSelected;
	
	public NewProposalViewWizardController(String wizardTitle, Topic topicSelected) {
		super(wizardTitle);
		this.topicSelected = topicSelected;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewProposalViewWizardPage page = (NewProposalViewWizardPage) super.getPage();
		String nameText = page.getNameText();
		String descriptionText = page.getDescriptionText();
		String category = page.getItemCategory();
		// TODO: cambiar estado;
		
		Proposal newProposal = new Proposal(nameText, descriptionText, new Date(), Categories.valueOf(category), 0);
		
		return runNewProposal(newProposal, topicSelected);
	}
}
