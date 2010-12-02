package gcad.wizards;

import gcad.internationalization.BundleInternationalization;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class NewProjectWizardPage extends WizardPage {

	private Text nameText;
	private Text descriptionText;
	private DateTime startDateText;
	private DateTime endDateText;
	private Text budgetText;
	private Text quantityLinesText;
	private Text domainText;
	private Combo progLanguageText;
	
	protected NewProjectWizardPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		// TODO: el rol se sabe por el usuario que inicia sesion
		
		Composite container = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayout(new GridLayout());
		
		Label nameLabel = new Label(container, SWT.NULL);
		nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		nameLabel.setText(BundleInternationalization.getString("NameLabel")+":");
		nameText.setLayoutData(gd);
		// Listener to validate the project name when user finishes writing
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		Label descriptionLabel = new Label(container, SWT.NULL);
		descriptionText = new Text(container, SWT.BORDER | SWT.SINGLE);
		descriptionLabel.setText(BundleInternationalization.getString("DescriptionLabel")+":");
		descriptionText.setLayoutData(gd);
		// Listener to validate the project description when user finishes writing
		descriptionText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});		
		
		Label startDateLabel = new Label(parent, SWT.NULL);
		startDateText = new DateTime(parent, SWT.DATE | SWT.BORDER | SWT.SINGLE);
		startDateLabel.setText(BundleInternationalization.getString("StartDateLabel")+":");	
		startDateText.setLayoutData(gd);
		

		Label endDateLabel = new Label(parent, SWT.NULL);
		endDateText = new DateTime(parent, SWT.DATE | SWT.BORDER | SWT.SINGLE);
		endDateLabel.setText(BundleInternationalization.getString("EndDateLabel")+":");	
		endDateText.setLayoutData(gd);
		
		Label budgetLabel = new Label(container, SWT.NULL);
		budgetText = new Text(container, SWT.BORDER | SWT.SINGLE);
		budgetLabel.setText(BundleInternationalization.getString("BudgetLabel")+":");
		budgetText.setLayoutData(gd);
		// TODO: cambiar este validador para que sea un numero double
		budgetText.addListener (SWT.Verify, new Listener () {
            public void handleEvent (Event event) {
                    String text = event.text;
                    for (int i=0; i<text.length (); i++) {
                            char ch = text.charAt (i);
                            if (!('0' <= ch && ch <= '9')) {
                                    event.doit = false;
                                    return;
                            }
                    }
            }
    }); 
		
		wizardChanged();
		setControl(container);

	}
	
	/** 
	 * This method validates the Database IP and Database Port
	 */
	private void wizardChanged() {
		
		boolean valid = true;
		
		// The user name can't be empty
		if (loginText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.UserEmpty"));
			valid = false;
		}
		
		// The name format must be correct
		if (valid && !validateName(loginText.getText())) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.UserFormat"));
			valid = false;
		}
				
		// The password can't be empty
		if (valid && passText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.PassEmpty"));
			valid = false;
		}
		
		// TODO: The password format must be correct
		/*if (valid && !validatePass(passText.getText())) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.PassFormat"));
			valid = false;
		}*/
		
		// The IP text can't be empty
		if (valid && IPText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.IPEmpty"));
			valid = false;
		}
		
		// The IP format must be correct
		if (valid && !validateIP(IPText.getText())) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.IPFormat"));
			valid = false;
		}
		
		// The port text can't be empty
		if (valid && portText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.PortEmpty"));
			valid = false;
		}
		
		// The port number must be correct
		if (valid && !validatePort(portText.getText())) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.PortRange") + " [" + MINIMUM_PORT + "-" + MAXIMUM_PORT + "]");
			valid = false;
		}

		if (valid) 
			updateStatus(null);
	}


}
