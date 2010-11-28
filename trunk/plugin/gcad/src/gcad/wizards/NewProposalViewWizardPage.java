package gcad.wizards;

import gcad.internationalization.BundleInternationalization;

import org.eclipse.swt.widgets.Composite;

/**
 * This abstract class represents a New Proposal Wizard Page when it is shown since the "Proposals" view
 */
public class NewProposalViewWizardPage extends AbstractNewProposalWizardPage {
	
	public NewProposalViewWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewProposalWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewProposalWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	}
			
}
