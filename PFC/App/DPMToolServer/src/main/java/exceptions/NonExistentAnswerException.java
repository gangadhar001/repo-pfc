package exceptions;

public class NonExistentAnswerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8195615216845671370L;

	public NonExistentAnswerException(String message) {
		super(message);
	}

}
