package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import model.business.knowledge.Knowledge;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This abstract class represents a Wizard Page to include common controls of the knowledge classes  
 */
public abstract class AbstractKnowledgeWP extends WizardPage {
	
	private Text titleText;
	private Text descriptionText;
	private Composite container;
	private boolean valid;
	
	protected AbstractKnowledgeWP(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		container = parent;
		GridData gd = new GridData(GridData.FILL_BOTH);
		
		Label titleLabel = new Label(container, SWT.NULL);
		titleText = new Text(container, SWT.BORDER | SWT.SINGLE);
		titleLabel.setText(BundleInternationalization.getString("TitleLabel")+":");	
		titleText.setLayoutData(gd);
		// Listener to validate the name
		titleText.addModifyListener(new ModifyListener() {
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
	 * This method is used to fill the data of the wizard page when modify a Knowledge
	 */
	protected void fillData(Knowledge k) {
		if (k!=null) {
			titleText.setText(k.getTitle());
			descriptionText.setText(k.getDescription());
			wizardChanged();
		}
	}
	
	/**
	 * This method validates the common controls
	 */
	protected void wizardChanged() {
		valid = true;
		
		// The title text can't be empty
		if (titleText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.TitleEmpty"));
			valid = false;
		}
		
		// The description text can't be empty
		if (valid && descriptionText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.DescriptionEmpty"));
			valid = false;
		}
		
		if (valid) 
			updateStatus(null);
	}

	protected void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}

	public String getTitleText() {
		return titleText.getText();
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
