package presentation.wizards.control.knowledge;

import java.util.Date;

import presentation.wizards.knowledge.ModifyProposalWP;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

/**
 * This class represents a Wizard Controller to modify a proposal when it is invoke since a view
 */
public class ModifyProposalViewWC extends AbstractModifyKnowledgeWC{

	private Proposal proposalSelected;

	public ModifyProposalViewWC(String wizardTitle, Proposal proposalSelected) {
		super(wizardTitle);
		this.proposalSelected = proposalSelected;
	}

	@Override
	public boolean performFinish() {
		ModifyProposalWP page = (ModifyProposalWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		String category = page.getItemCategory();
		Topic newParentTopic = (Topic) page.getTopics().get(page.getItemCbTopics());
		Proposal newProposal = new Proposal(title, description, new Date(), Categories.valueOf(category));
		// Set the proposal id with the id of the old proposal
		newProposal.setId(proposalSelected.getId());
			
		return runModifyProposal(newProposal, proposalSelected, newParentTopic);
	}

}
