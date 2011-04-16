package presentation.utils.validation;

import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Class used to validate a user name
 * 
 */
public class UserNameValidator extends AbstractValidator {

	public UserNameValidator(JFrame parent, JTextField c, String message) {
		super(parent, c, message);
	}

	@Override
	protected boolean validationCriteria(JComponent c) {
		Pattern pattern;
		pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]+");
	    return pattern.matcher(((JTextField)c).getText()).matches();    		
	}
}
