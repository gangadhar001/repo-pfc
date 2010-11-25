package gcad.wizards;

import java.util.regex.Pattern;

import gcad.internationalization.BundleInternationalization;

import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewProposalWizardPage extends WizardPage {
	
	private Text nameText;
	private Text descriptionText;
	private Combo categoryChk;
	

	protected NewProposalWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewProposalWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewProposalWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		GridData gd = new GridData(GridData.FILL_BOTH);
		
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
		descriptionText = new Text(container, SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		descriptionLabel.setText(BundleInternationalization.getString("DescriptionLabel")+":");	
		descriptionText.setLayoutData(gd);
		// Listener to validate the description 
		descriptionText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		Label categoryLabel = new Label(container, SWT.NONE);
		categoryChk = new Combo(container, SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		categoryLabel.setText(BundleInternationalization.getString("CategoryLabel")+":");	
		categoryChk .setLayoutData(gd);
		
		wizardChanged();
		setControl(container);

	}
	
	// This method validates the wizard
	private void wizardChanged() {
		
		boolean valid = true;
		
		// The name text can't be empty
		if (nameText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.NameEmpty"));
			valid = false;
		}
		
		// The description text can't be empty
		if (valid && descriptionText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.DescriptionEmptyFormat"));
			valid = false;
		}
		
		if (valid) 
			updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}
	
}
