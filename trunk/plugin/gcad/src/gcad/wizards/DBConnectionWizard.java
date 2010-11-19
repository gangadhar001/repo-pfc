package gcad.wizards;

import gcad.internationalization.BundleInternationalization;

import org.eclipse.jface.wizard.Wizard;

public class DBConnectionWizard extends Wizard {
	
	private DBConnectionWizardPage page;

	public DBConnectionWizard () {
		super();
		setWindowTitle(BundleInternationalization.getString("DBConnectionPageTitle"));
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		page = new DBConnectionWizardPage(BundleInternationalization.getString("DBConnectionPageTitle"));
		addPage(page);
	}
	
	@Override
	public boolean performFinish() {
		// TODO: conectar al pulsar Finish, despues de validar
		return false;
	}

}
