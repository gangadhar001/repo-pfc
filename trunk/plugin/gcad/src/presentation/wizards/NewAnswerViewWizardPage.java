package presentation.wizards;

import internationalization.BundleInternationalization;

import org.eclipse.swt.widgets.Composite;

/**
 * This abstract class represents a New Answer Wizard Page when it is shown since the "Proposals" view
 */
public class NewAnswerViewWizardPage extends AbstractNewKnowledgeWizardPage {
	
	public NewAnswerViewWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewAnswerWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewAnswerWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	}
			
}
