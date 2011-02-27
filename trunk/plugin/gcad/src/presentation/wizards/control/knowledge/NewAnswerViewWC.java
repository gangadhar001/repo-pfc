package presentation.wizards.control.knowledge;



import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.knowledge.AnswerViewWP;

/**
 * This abstract class represents a New Answer Wizard Controller when it is shown since a view
 */
public class NewAnswerViewWC extends AbstractNewKnowledgeWC {

	private Proposal proposalSelected;
	
	public NewAnswerViewWC(String wizardTitle, Proposal proposalSelected) {
		super(wizardTitle);
		this.proposalSelected = proposalSelected;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		AnswerViewWP page = (AnswerViewWP) super.getPage();
		String title = page.getTitleText();
		String descriptionText = page.getDescriptionText();
		// TODO: añadir argumento
		final Answer newAnswer = new Answer(title, descriptionText, new Date(), "Pro");

		return runNewAnswer(newAnswer, proposalSelected);
	}
}
