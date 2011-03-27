package exceptions;

import internationalization.BundleInternationalization;

public class InvalidIPException extends Exception {

	private static final long serialVersionUID = -1166896376103239365L;

	public InvalidIPException() {
		super(BundleInternationalization.getString("InvalidIP_Exception"));
	}
	
	public InvalidIPException(String message) {
		super(message);
	}
	
}
