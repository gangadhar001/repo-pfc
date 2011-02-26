package presentation.wizards.control.knowledge;

import java.util.Date;

import presentation.wizards.knowledge.ModifyProposalMenuWP;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;

/**
 * This class represents a Wizard Controller to modify an answer when it is invoke since a view
 */
public class ModifyProposalViewWC extends AbstractModifyKnowledgeWC{

	private Proposal proposalSelected;

	public ModifyProposalViewWC(String wizardTitle, Proposal proposalSelected) {
		super(wizardTitle);
		this.proposalSelected = proposalSelected;
	}

	@Override
	public boolean performFinish() {
		ModifyProposalMenuWP page = (ModifyProposalMenuWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		String category = page.getItemCategory();
		Topic newParentTopic = (Topic) page.getTopics().get(page.getItemCbProposals());
		Proposal newProposal = new Proposal(title, description, new Date(), Categories.valueOf(category));
		// Set the proposal id with the id of the old answer
		newProposal.setId(proposalSelected.getId());
			
		return runModifyProposal(newProposal, proposalSelected, newParentTopic);
	}

}
