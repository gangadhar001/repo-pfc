package exceptions;

import internationalization.BundleInternationalization;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = -1166896376103239365L;

	public InvalidPasswordException() {
		super(BundleInternationalization.getString("InvalidPassword_Exception"));
	}
	
	public InvalidPasswordException(String message) {
		super(message);
	}
	
}
