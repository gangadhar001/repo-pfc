package presentation.wizards.knowledge;

import internationalization.BundleInternationalization;

import model.business.knowledge.Answer;
import model.business.knowledge.Knowledge;
import model.business.knowledge.Proposal;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This abstract class represents a New Proposal Wizard Page
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
		// TODO: poner FILL_BOTH y ajustar la altura de cada componente
		GridData gd = new GridData(GridData.FILL_BOTH);
		
		Label nameLabel = new Label(container, SWT.NULL);
		titleText = new Text(container, SWT.BORDER | SWT.SINGLE);
		nameLabel.setText(BundleInternationalization.getString("NameLabel")+":");	
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
	
	public void fillData(Knowledge k) {
		if (k!=null) {
			titleText.setText(k.getTitle());
			if (k instanceof Proposal)
				descriptionText.setText(((Proposal)k).getDescription());
			if (k instanceof Answer)
				descriptionText.setText(((Answer)k).getDescription());
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
