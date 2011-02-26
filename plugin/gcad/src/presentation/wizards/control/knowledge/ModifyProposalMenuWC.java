package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import presentation.wizards.knowledge.ModifyProposalMenuWP;

/**
 * This class represents a Wizard Controller to modify a proposal when it is invoke since the menu
 */
public class ModifyProposalMenuWC extends AbstractModifyKnowledgeWC {

	public ModifyProposalMenuWC(String wizardTitle) {
		super(wizardTitle);
	}

	@Override
	public boolean performFinish() {
		ModifyProposalMenuWP page = (ModifyProposalMenuWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		String category = page.getItemCategory();
		Topic newParentTopic = (Topic) page.getTopics().get(page.getItemCbProposals());
		Proposal oldProposal = (Proposal) page.getProposals().get(page.getItemCbProposals());
		Proposal newProposal = new Proposal(title, description, new Date(), Categories.valueOf(category));
		// Set the proposal id with the id of the old answer
		newProposal.setId(oldProposal.getId());
			
		return runModifyProposal(newProposal, oldProposal, newParentTopic);
	}
	
	
}


