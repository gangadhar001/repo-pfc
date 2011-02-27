package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.knowledge.ModifyAnswerWP;

/**
 * This class represents a Wizard Controller to modify an answer when it is invoke since the menu
 */
public class ModifyAnswerMenuWC extends AbstractModifyKnowledgeWC {
	
	public ModifyAnswerMenuWC(String wizardTitle) {
		super(wizardTitle);
	}

	@Override
	public boolean performFinish() {
		ModifyAnswerWP page = (ModifyAnswerWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		Proposal newParentProposal = (Proposal) page.getProposals().get(page.getItemCbProposals());
		Answer oldAnswer = (Answer) page.getAnswers().get(page.getItemCbAnswers());
		// TODO: añadir argumento
		Answer newAnswer = new Answer(title, description, new Date(), "Pro");
		// Set the answer id with the id of the old answer
		newAnswer.setId(oldAnswer.getId());
			
		return runModifyAnswer(newAnswer, oldAnswer, newParentProposal);
	}

}
