package presentation.utils.validation;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// Reference: http://www.javalobby.org/java/forums/t20552.html
/**
 * Class used to validate a mandatory text field
 * 
 */
public class NotEmptyValidator extends AbstractValidator {

	public NotEmptyValidator(JFrame parent, JTextField c, String message) {
		super(parent, c, message);
	}

	@Override
	protected boolean validationCriteria(JComponent c) {
		boolean valid = true;
		String text = "";
		if (c instanceof JTextField)
			text = ((JTextField)c).getText();
		else if (c instanceof JPasswordField)
			text = new String(((JPasswordField)c).getPassword());
		if (text.equals(""))
			valid = false;
		return valid;
	}

}
