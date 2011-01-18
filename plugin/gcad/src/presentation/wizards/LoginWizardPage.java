package presentation.wizards;


import internationalization.BundleInternationalization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This abstract class represents a DB Connection Wizard Page
 */
public class LoginWizardPage extends WizardPage {
	
	private Text loginText;
	private Text passText;
	private Text IPText;
	private Text portText;

	private static final int MINIMUM_PORT = 1;
	private static final int MAXIMUM_PORT = 65535;
	public static final int MAXIMUM_STRING_LENGTH = 255;
	public static final int MINIMUM_LENGHT_PASSWORD = 8;

	public LoginWizardPage(String pageName) {
		super(pageName);
		setTitle(BundleInternationalization.getString("LoginWizardTitle"));
		setDescription(BundleInternationalization.getString("LoginPageDescription"));
	}

	@Override
	public void createControl(Composite parent) {		
		// TODO: el rol se sabe por el usuario que inicia sesion
		
		Composite container = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayout(new GridLayout());

		Group groupLogin = new Group(container, SWT.NONE);
		groupLogin.setLayout(new GridLayout(2,false));
		groupLogin.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		groupLogin.setText("Login information");
		
		Group groupDB = new Group(container, SWT.NONE);
		groupDB.setLayout(new GridLayout(2,false));
		groupDB.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		groupDB.setText("Database connection information");
		
		Label loginLabel = new Label(groupLogin, SWT.NULL);
		loginText = new Text(groupLogin, SWT.BORDER | SWT.SINGLE);
		loginLabel.setText(BundleInternationalization.getString("UserLabel")+":");
		loginText.setLayoutData(gd);
		// Listener to validate the user name when user finishes writing
		loginText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});
		
		Label passLabel = new Label(groupLogin, SWT.NULL);
		passText = new Text(groupLogin, SWT.BORDER | SWT.SINGLE);
		passLabel.setText(BundleInternationalization.getString("PassLabel")+":");
		passText.setLayoutData(gd);
		passText.setEchoChar('*');
		// Listener to validate the password when user finishes writing
		passText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});		
		
		Label IPlabel = new Label(groupDB, SWT.NULL);
		IPText = new Text(groupDB, SWT.BORDER | SWT.SINGLE);
		IPlabel.setText(BundleInternationalization.getString("IPLabel")+":");	
		IPText.setLayoutData(gd);
		// Listener to validate the IP when user finishes writing
		IPText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wizardChanged();
			}
		});

		Label portLabel = new Label(groupDB, SWT.NULL);
		portText = new Text(groupDB, SWT.BORDER | SWT.SINGLE);
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

	private boolean validatePass(String pass) {
		Pattern passwordPattern;
	    Matcher matcher;
		boolean valid = true;
		
		if(pass.length() > MAXIMUM_STRING_LENGTH) {
			valid=false;
		}

		if(valid && pass.length() >= MINIMUM_LENGHT_PASSWORD) {
			passwordPattern = Pattern.compile("[a-zA-Z0-9]+");
		    matcher = passwordPattern.matcher(pass);
		    if(!matcher.matches())
	    		valid = false;
		}
		return valid;
	}
	
	// A user name is valid if all are alfanumeric characters, begining with a letter
	private boolean validateName(String name) {
		Pattern patronUsuario;
	    Matcher matcher;
		boolean valid = true;
		
		if(name.length() > MAXIMUM_STRING_LENGTH) {
			valid = false;
		}
		
		if(valid && name.length() > 0) {
			patronUsuario = Pattern.compile("[a-zA-Z][a-zA-Z0-9]+");
		    matcher = patronUsuario.matcher(name);
		    if(!matcher.matches())
	    		valid = false;
		}
		return valid;
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

	public String getIPText() {
		return IPText.getText();
	}

	public String getPortText() {
		return portText.getText();
	}

	public String getLoginText() {
		return loginText.getText();
	}

	public String getPassText() {
		return passText.getText();
	}
	
	
}
