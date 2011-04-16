package exceptions;

import internationalization.BundleInternationalization;

public class NotLoggedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1628256207265866072L;

	public NotLoggedException() {
		super(BundleInternationalization.getString("NotLogged_Exception"));
	}
	
	public NotLoggedException(String message) {
		super(message);
	}

}
