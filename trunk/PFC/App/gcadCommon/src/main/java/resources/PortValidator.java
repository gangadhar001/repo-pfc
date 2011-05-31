package resources;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Class used to validate the port field
 * 
 */
public class PortValidator extends AbstractValidator {

	public static final int MINIMUM_PORT = 1;
	public static final int MAXIMUM_PORT = 65535;
	
	public PortValidator(JFrame jFrame, JComponent c, String message) {
		super(jFrame, c, message);
	}

	@Override
	protected boolean validationCriteria(JComponent c) {
		boolean valid = true;
		try {
			int portNumber = Integer.parseInt((((JTextField)c).getText()));
			if(portNumber < MINIMUM_PORT || portNumber > MAXIMUM_PORT) {
				valid = false;		
			}
		} catch(NumberFormatException ex) {
			valid = false;
		}
		return valid;
	}

}
