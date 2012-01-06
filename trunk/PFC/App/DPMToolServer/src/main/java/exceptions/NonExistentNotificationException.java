package exceptions;

public class NonExistentNotificationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2867546019625202246L;

	public NonExistentNotificationException(String message) {
		super(message);
	}

}
