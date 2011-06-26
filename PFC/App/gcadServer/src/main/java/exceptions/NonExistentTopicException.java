package exceptions;

public class NonExistentTopicException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6841763881559784916L;

	public NonExistentTopicException(String message) {
		super(message);
	}

}
