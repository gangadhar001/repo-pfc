package presentation.wizards.knowledge;


import internationalization.BundleInternationalization;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This abstract class represents a DB Connection Wizard Page
 */
public class TopicWP extends AbstractKnowledgeWP {
		
	public TopicWP(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewTopicWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewTopicWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {	
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		container.setLayout(layout);				
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		container.layout();
		wizardChanged();
	}
	
}
