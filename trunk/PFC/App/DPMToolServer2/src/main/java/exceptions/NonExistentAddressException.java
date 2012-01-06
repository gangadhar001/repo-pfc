package exceptions;

public class NonExistentAddressException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1886322396651115501L;

	public NonExistentAddressException(String message) {
		super(message);
	}

}
