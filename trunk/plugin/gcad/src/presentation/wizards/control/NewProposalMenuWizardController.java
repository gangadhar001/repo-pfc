package presentation.wizards.control;

import java.util.Date;

import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import presentation.wizards.NewProposalMenuWizardPage;

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
		String nameText = page.getNameText();
		String descriptionText = page.getDescriptionText();
		String category = page.getItemCategory();
		int indeParentTopic = page.getItemCbTopics();
		Topic parentTopic = (Topic) page.getTopics().get(indeParentTopic);
		Proposal newProposal = new Proposal(nameText, descriptionText, new Date(), Categories.valueOf(category));
			
		return runNewProposal(newProposal, parentTopic);
	}
	
}


