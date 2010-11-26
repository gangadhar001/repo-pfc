package gcad.wizards;

import gcad.internationalization.BundleInternationalization;

import org.eclipse.swt.widgets.Composite;

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
