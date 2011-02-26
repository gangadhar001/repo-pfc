package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import presentation.wizards.knowledge.NewProposalMenuWP;

/**
 * This abstract class represents a New Proposal Wizard when it is invoke since the "Knowledge" menu
 */
public class NewProposalMenuWC extends AbstractNewKnowledgeWC {

	public NewProposalMenuWC(String wizardTitle) {
		super(wizardTitle);
	}
	
	@Override
	public boolean performFinish() {
		NewProposalMenuWP page = (NewProposalMenuWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		String category = page.getItemCategory();
		int indeParentTopic = page.getItemCbTopics();
		Topic parentTopic = (Topic) page.getTopics().get(indeParentTopic);
		Proposal newProposal = new Proposal(title, description, new Date(), Categories.valueOf(category));
			
		return runNewProposal(newProposal, parentTopic);
	}
	
}


