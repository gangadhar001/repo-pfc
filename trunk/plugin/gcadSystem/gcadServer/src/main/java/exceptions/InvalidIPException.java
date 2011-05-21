package exceptions;

import internationalization.AppInternationalization;

public class InvalidIPException extends Exception {

	private static final long serialVersionUID = -1166896376103239365L;

	public InvalidIPException() {
		super(AppInternationalization.getString("InvalidIP_Exception"));
	}
	
	public InvalidIPException(String message) {
		super(message);
	}
	
}
