package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.knowledge.NewAnswerMenuWP;

/**
 * This abstract class represents a New Proposal Wizard Controller when it is shown since the "Knowledge" menu
 */
public class NewAnswerMenuWC extends AbstractNewKnowledgeWC {

	public NewAnswerMenuWC(String wizardTitle) {
		super(wizardTitle);
	}
	
	@Override
	public boolean performFinish() {
		NewAnswerMenuWP page = (NewAnswerMenuWP) super.getPage();
		String title = page.getTitleText();
		String descriptionText = page.getDescriptionText();
		int indeParentProposal = page.getItemCbProposals();
		Proposal parentProposal = (Proposal) page.getProposals().get(indeParentProposal);
		// TODO: añadir argumento
		Answer newAnswer = new Answer(title, descriptionText, new Date(), "Pro");
			
		return runNewAnswer(newAnswer, parentProposal);
	}
	
}


