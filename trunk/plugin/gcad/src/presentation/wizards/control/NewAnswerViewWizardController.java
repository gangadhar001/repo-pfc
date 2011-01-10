package presentation.wizards.control;


import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.NewAnswerViewWizardPage;

/**
 * This abstract class represents a New Answer Wizard when it is shown since the "Proposals" view
 */
public class NewAnswerViewWizardController extends AbstractNewKnowledgeWizardController {

	private Proposal proposalSelected;
	public NewAnswerViewWizardController(String wizardTitle, Proposal proposalSelected) {
		super(wizardTitle);
		this.proposalSelected = proposalSelected;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewAnswerViewWizardPage page = (NewAnswerViewWizardPage) super.getPage();
		final String nameText = page.getNameText();
		final String descriptionText = page.getDescriptionText();
		Answer newAnswer = new Answer(nameText, descriptionText, new Date());
		// TODO: cambiar argumento
		return super.run(newAnswer, proposalSelected);
	}
}
