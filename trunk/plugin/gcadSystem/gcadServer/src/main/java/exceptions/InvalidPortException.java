package exceptions;

import internationalization.AppInternationalization;


/**
 */
public class InvalidPortException extends Exception {

	private static final long serialVersionUID = 2579891266483892738L;

	public InvalidPortException() {
		super(AppInternationalization.getString("InvalidPort_Exception"));
	}
	
	public InvalidPortException(String message) {
		super(message);
	}
	
}
