package presentation.wizards.control;

import java.util.Date;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import presentation.wizards.NewAnswerMenuWizardPage;

/**
 * This abstract class represents a New Proposal Wizard when it is shown since the "Knowledge" menu
 */
public class NewAnswerMenuWizardController extends AbstractNewKnowledgeWizardController {

	public NewAnswerMenuWizardController(String wizardTitle) {
		super(wizardTitle);
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		NewAnswerMenuWizardPage page = (NewAnswerMenuWizardPage) super.getPage();
		String nameText = page.getNameText();
		String descriptionText = page.getDescriptionText();
		int indeParentProposal = page.getItemCbProposals();
		Proposal parentProposal = (Proposal) page.getProposals().get(indeParentProposal);
		// TODO: añadir argumento
		Answer newAnswer = new Answer(nameText, descriptionText, new Date(), " ");
			
		return runNewAnswer(newAnswer, parentProposal);
	}
	
}


