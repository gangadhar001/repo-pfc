package gcad.wizards;

import java.util.regex.Pattern;

import gcad.internationalization.BundleInternationalization;

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
		// Listener to validate the IP when user finishes writing
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});

		Label portLabel = new Label(container, SWT.NULL);
		portText = new Text(container, SWT.BORDER | SWT.SINGLE);
		// TODO: se dice Database Port o Port Database???
		portLabel.setText(BundleInternationalization.getString("PortLabel")+":");	
		portText.setLayoutData(gd);
		// Listener to validate the Port when user finishes writing
		portText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		wizardChanged();
		setControl(container);

	}
	
	// TODO: duda ingles (database ip o al reves) This method validates the Database IP and Database Port
	private void wizardChanged() {
		
		boolean valid = true;
		
		// The IP text can't be empty
		if (IPText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.IPEmpty"));
			valid = false;
		}
		
		// The IP format must be correct
		if (!validateIP(IPText.getText())) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.IPFormat"));
			valid = false;
		}
		
		// The port text can't be empty
		if (portText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.PortEmpty"));
			valid = false;
		}
		
		// The port number must be correct
		if (!validatePort(portText.getText())) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.PortRange") + " [" + MINIMUM_PORT + "-" + MAXIMUM_PORT + "]");
			valid = false;
		}

		if (valid) 
			updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}
	
	private boolean validateIP (String IP) {
		Pattern IPPattern = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
		return IPPattern.matcher(IP).matches();
	}
	
	private boolean validatePort (String port){
		int portNumber;
		boolean valid = true;	
		try {
			portNumber = Integer.parseInt(port);
			if(portNumber < MINIMUM_PORT || portNumber > MAXIMUM_PORT) {
				valid = false;
			}
		} catch(NumberFormatException ex) {
			valid = false;
		}
		return valid;
	}
}
