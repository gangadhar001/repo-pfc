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
 * This abstract class represents a New Proposal Wizard Page
 */

public abstract class AbstractNewKnowledgeWizardPage extends WizardPage {
	
	private Text nameText;
	private Text descriptionText;
	private Composite container;
	private boolean valid;
	
	protected AbstractNewKnowledgeWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		commonControls();
	}
	
	/**
	 * This method creates and initialized common controls between the new proposal wizard pages
	 */
	private void commonControls () {	
		// TODO: poner FILL_BOTH y ajustar la altura de cada componente
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		container.setLayout(layout);		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		Label nameLabel = new Label(container, SWT.NULL);
		nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		nameLabel.setText(BundleInternationalization.getString("NameLabel")+":");	
		nameText.setLayoutData(gd);
		// Listener to validate the name
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});

		Label descriptionLabel = new Label(container, SWT.NULL);
		descriptionText = new Text(container, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		descriptionLabel.setText(BundleInternationalization.getString("DescriptionLabel")+":");
		GridData gdCombo = new GridData(GridData.FILL_HORIZONTAL);
		gdCombo.heightHint = 50;
		descriptionText.setLayoutData(gdCombo);
		// Listener to validate the description 
		descriptionText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		wizardChanged();
		setControl(container);

	}
	
	/**
	 * This method validates the common controls
	 */
	protected void wizardChanged() {
		valid = true;
		
		// The name text can't be empty
		if (nameText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.NameProposalEmpty"));
			valid = false;
		}
		
		// The description text can't be empty
		if (valid && descriptionText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.DescriptionProposalEmpty"));
			valid = false;
		}
		
		if (valid) 
			updateStatus(null);
	}

	protected void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}

	public String getNameText() {
		return nameText.getText();
	}

	public String getDescriptionText() {
		return descriptionText.getText();
	}
	
	public Composite getContainerParent() {
		return container;
	}

	public boolean isValid() {
		return valid;
	}
	
	



}
