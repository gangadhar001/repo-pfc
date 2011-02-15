package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.knowledge.NewAnswerMenuWP;

/**
 * This abstract class represents a New Proposal Wizard when it is shown since the "Knowledge" menu
 */
public class NewAnswerMenuWC extends AbstractNewKnowledgeWC {

	public NewAnswerMenuWC(String wizardTitle) {
		super(wizardTitle);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewAnswerMenuWP page = (NewAnswerMenuWP) super.getPage();
		String nameText = page.getNameText();
		String descriptionText = page.getDescriptionText();
		int indeParentProposal = page.getItemCbProposals();
		Proposal parentProposal = (Proposal) page.getProposals().get(indeParentProposal);
		// TODO: a�adir argumento
		Answer newAnswer = new Answer(nameText, descriptionText, new Date(), " ");
			
		return runNewAnswer(newAnswer, parentProposal);
	}
	
}


