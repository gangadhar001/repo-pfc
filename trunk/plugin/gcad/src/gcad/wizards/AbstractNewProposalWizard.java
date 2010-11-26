package gcad.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

public abstract class AbstractNewProposalWizard extends Wizard {

	private WizardPage page;
	
	public AbstractNewProposalWizard (String wizardTitle) {
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
