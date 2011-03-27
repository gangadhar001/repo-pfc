package exceptions;

import presentation.auxiliary.Validation;
import internationalization.BundleInternationalization;


/**
 */
public class InvalidPortException extends Exception {

	private static final long serialVersionUID = 2579891266483892738L;

	public InvalidPortException() {
		super(BundleInternationalization.getString("InvalidPort_Exception") + String.valueOf(Validation.MINIMUM_PORT) + " - " + String.valueOf(Validation.MAXIMUM_PORT) + ".");
	}
	
	public InvalidPortException(String message) {
		super(message);
	}
	
}
