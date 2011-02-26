package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.knowledge.ModifyAnswerMenuWP;

/**
 * This class represents a Wizard Controller to modify an answer when it is invoke since a view
 */
public class ModifyAnswerViewWC extends AbstractModifyKnowledgeWC {

	private Answer answerSelected;

	public ModifyAnswerViewWC(String wizardTitle, Answer answerSelected) {
		super(wizardTitle);
		this.answerSelected = answerSelected;
	}

	@Override
	public boolean performFinish() {
		ModifyAnswerMenuWP page = (ModifyAnswerMenuWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		Proposal newParentProposal = (Proposal) page.getProposals().get(page.getItemCbProposals());
		// TODO: añadir argumento
		Answer newAnswer = new Answer(title, description, new Date(), "Pro");
		// Set the answer id with the id of the old answer
		newAnswer.setId(answerSelected.getId());			
		return runModifyAnswer(newAnswer, answerSelected, newParentProposal);

	}

}
