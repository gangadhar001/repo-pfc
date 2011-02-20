package presentation.wizards.control.knowledge;

import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.knowledge.ModifyAnswerMenuWP;

public class ModifyAnswerMenuWC extends AbstractModifyKnowledgeWC {
	
	public ModifyAnswerMenuWC(String wizardTitle) {
		super(wizardTitle);
	}

	@Override
	public boolean performFinish() {
		ModifyAnswerMenuWP page = (ModifyAnswerMenuWP) super.getPage();
		String title = page.getTitleText();
		String description = page.getDescriptionText();
		Proposal parentProposal = (Proposal) page.getProposals().get(page.getItemCbProposals());
		// Set the answer id with the id of the old answer
		Answer oldAnswer = (Answer) page.getAnswers().get(page.getItemCbAnswers());
		// TODO: añadir argumento
		Answer answer = new Answer(title, description, new Date(), "Pro");
		answer.setId(oldAnswer.getId());
			
		return runModifyAnswer(answer, parentProposal);
	}

}
