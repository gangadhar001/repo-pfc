package presentation.wizards.control;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

/**
 * This abstract class represents a New Proposal Wizard
 */
public abstract class AbstractNewKnowledgeWizardController extends Wizard {
	
	private WizardPage page;
	
	public AbstractNewKnowledgeWizardController (String wizardTitle) {
		super();
		setWindowTitle(wizardTitle);
		setNeedsProgressMonitor(true);
		
	}
	
	public void addPages(WizardPage page) {
		super.addPages();
		this.page=page;
		addPage(page);
	}

	public WizardPage getPage() {
		return page;
	}
		
}
