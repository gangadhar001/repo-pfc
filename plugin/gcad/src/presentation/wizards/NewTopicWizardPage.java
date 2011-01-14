package presentation.wizards;


import internationalization.BundleInternationalization;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This abstract class represents a DB Connection Wizard Page
 */
public class NewTopicWizardPage extends WizardPage {
	
	private Text topicText;
	
	public NewTopicWizardPage(String pageName) {
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
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		Label topicLabel = new Label(container, SWT.NONE);
		topicText = new Text(container, SWT.BORDER | SWT.SINGLE);
		topicLabel.setText(BundleInternationalization.getString("TopicLabel")+":");
		topicText.setLayoutData(gd);
		// Listener to validate the user name when user finishes writing
		topicText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		wizardChanged();
		setControl(container);

	}
	
	/** 
	 * This method validates the topic name
	 */
	private void wizardChanged() {
		
		boolean valid = true;
		
		// The topic name can't be empty
		if (topicText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.UserEmpty"));
			valid = false;
		}
		
		if (valid) 
			updateStatus(null);
	}
		
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}

	public String getTopicText() {
		return topicText.getText();
	}
	
	
	
}
