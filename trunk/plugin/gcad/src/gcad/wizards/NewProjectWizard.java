package gcad.wizards;

import gcad.internationalization.BundleInternationalization;

import org.eclipse.jface.wizard.Wizard;

public class NewProjectWizard extends Wizard {

	private NewProjectWizardPage page;

	public NewProjectWizard () {
		super();
		setWindowTitle(BundleInternationalization.getString("NewProjectWizard"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		super.addPages();
		page = new NewProjectWizardPage(BundleInternationalization.getString("NewProjectWizardPageTitle"));
		addPage(page);
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
