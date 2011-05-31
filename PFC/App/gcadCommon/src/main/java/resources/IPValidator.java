package resources;

import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Class used to validate an IP address field
 * 
 */
public class IPValidator extends AbstractValidator {

	public IPValidator(JFrame jFrame, JComponent c, String message) {
		super(jFrame, c, message);
	}

	@Override
	protected boolean validationCriteria(JComponent c) {
		Pattern pattern;
		pattern = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
		
		return pattern.matcher(((JTextField)c).getText()).matches();
	}
}
