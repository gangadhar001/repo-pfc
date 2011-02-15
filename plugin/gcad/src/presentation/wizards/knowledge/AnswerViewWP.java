package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This abstract class represents a New Answer Wizard Page when it is shown since the "Proposals" view
 */
public class AnswerViewWP extends AbstractKnowledgeWP {
	
	public AnswerViewWP(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewAnswerWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewAnswerWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		container.setLayout(layout);	
		super.createControl(container);
		container.layout();
	}
			
}
