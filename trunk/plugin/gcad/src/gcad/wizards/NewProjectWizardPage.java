package gcad.wizards;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gcad.internationalization.BundleInternationalization;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
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
	private Combo progLanguageCombo;
	private Text estimatedHoursText;
	
	public NewProjectWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("NewProjectWizardPageTitle"));
		setDescription(BundleInternationalization.getString("NewProjectWizardPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		// TODO: el rol se sabe por el usuario que inicia sesion
		
		Composite container = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayout(new GridLayout(2, false));
		
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
		
		Label startDateLabel = new Label(container, SWT.NULL);
		startDateText = new DateTime(container, SWT.DATE | SWT.BORDER | SWT.SINGLE);
		startDateLabel.setText(BundleInternationalization.getString("StartDateLabel")+":");	
		startDateText.setLayoutData(gd);
		

		Label endDateLabel = new Label(container, SWT.NULL);
		endDateText = new DateTime(container, SWT.DATE | SWT.BORDER | SWT.SINGLE);
		endDateLabel.setText(BundleInternationalization.getString("EndDateLabel")+":");	
		endDateText.setLayoutData(gd);
		
		Label budgetLabel = new Label(container, SWT.NULL);
		budgetText = new Text(container, SWT.BORDER | SWT.SINGLE);
		budgetLabel.setText(BundleInternationalization.getString("BudgetLabel")+":");
		budgetText.setLayoutData(gd);
		budgetText.addListener (SWT.Verify, new Listener () {
            public void handleEvent (Event event) {
            	validateDouble(event);
            }
		}); 
		budgetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		Label quantityLinesLabel = new Label(container, SWT.NULL);
		quantityLinesText = new Text(container, SWT.BORDER | SWT.SINGLE);
		quantityLinesLabel.setText(BundleInternationalization.getString("NumberCodeLinesLabel")+":");
		quantityLinesText.setLayoutData(gd);
		quantityLinesText.addListener (SWT.Verify, new Listener () {
            public void handleEvent (Event event) {
            	validateInt(event);
            }

		});
		quantityLinesText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		Label domainLabel = new Label(container, SWT.NULL);
		domainText = new Text(container, SWT.BORDER | SWT.SINGLE);
		domainLabel.setText(BundleInternationalization.getString("DomainLabel")+":");
		domainText.setLayoutData(gd);
		// Listener to validate the domain text when user finishes writing
		domainText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		Label progLanguageLabel = new Label(container, SWT.NULL);
		progLanguageCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		progLanguageLabel.setText(BundleInternationalization.getString("ProgrammingLanguageLabel")+":");
		// progLanguageCombo.setLayoutData(gd);	
		progLanguageCombo.setItems (new String [] {"Java", "Ada", "C++", "C#", "Cobol", "ASP", "JSP"});
		progLanguageCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				wizardChanged();
				
			}
		});
		
		Label estimatedHoursLabel = new Label(container, SWT.NULL);
		estimatedHoursText = new Text(container, SWT.BORDER | SWT.SINGLE);
		estimatedHoursLabel.setText(BundleInternationalization.getString("EstimatedHoursLabel")+":");
		estimatedHoursText.setLayoutData(gd);
		estimatedHoursText.addListener (SWT.Verify, new Listener () {
            public void handleEvent (Event event) {
            	validateInt(event);
            }

		});
		estimatedHoursText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
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
		
		// The project name can't be empty
		if (nameText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.NameEmpty"));
			valid = false;
		}
				
		// The project description can't be empty
		if (valid && descriptionText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.DescriptionEmpty"));
			valid = false;
		}
		
		// TODO: validar que la fecha de inicio sea anterior a la de final
				
		// The project budget can't be empty
		if (valid && budgetText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.BudgetEmpty"));
			valid = false;
		}
		
		// The quantity of lines of the project can't be empty
		if (valid && quantityLinesText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.NumberCodeLinesEmpty"));
			valid = false;
		}
		
		// The project domain of the project can't be empty
		if (valid && domainText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.DomainEmpty"));
			valid = false;
		}
		
		if (valid && progLanguageCombo.getSelectionIndex()==-1) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.ProgrammingLanguageNotSelected"));
			valid = false;
		}
		
		if (valid && estimatedHoursText.getText().length() == 0) {
			updateStatus(BundleInternationalization.getString("ErrorMessage.EstimatedHoursEmpty"));
			valid = false;
		}
		
		if (valid) 
			updateStatus(null);
		
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}
	
	private void validateInt(Event event) {
		String text = event.text;
		Pattern intPattern = Pattern.compile("[0-9]");
		Matcher matcher = intPattern.matcher(text);
		if (!matcher.matches()) {
			event.doit = false;
            return;
        }
	}
	
	private void validateDouble(Event event) {
		String text = event.text;
		Pattern doublePattern = Pattern.compile("[0-9]|(\\.)");
		Matcher matcher = doublePattern.matcher(text);
		if ((budgetText.getText().contains(".") && text.equals(".")) || !matcher.matches()) {
			event.doit = false;
			return;
        }		
	}


}
