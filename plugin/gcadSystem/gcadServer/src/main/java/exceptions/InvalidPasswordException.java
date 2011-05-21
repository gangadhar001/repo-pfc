package exceptions;

import internationalization.AppInternationalization;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = -1166896376103239365L;

	public InvalidPasswordException() {
		super(AppInternationalization.getString("InvalidPassword_Exception"));
	}
	
	public InvalidPasswordException(String message) {
		super(message);
	}
	
}
