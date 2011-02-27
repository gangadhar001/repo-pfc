package presentation.wizards.control.knowledge;


import java.util.Date;

import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import presentation.wizards.knowledge.ProposalViewWP;

/**
 * This abstract class represents a New Proposal Wizard Controller when it is shown since a view
 */
public class NewProposalViewWC extends AbstractNewKnowledgeWC {

	private Topic topicSelected;
	
	public NewProposalViewWC(String wizardTitle, Topic topicSelected) {
		super(wizardTitle);
		this.topicSelected = topicSelected;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		ProposalViewWP page = (ProposalViewWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		String category = page.getItemCategory();		
		Proposal newProposal = new Proposal(title, description, new Date(), Categories.valueOf(category));
		
		return runNewProposal(newProposal, topicSelected);
	}
}
