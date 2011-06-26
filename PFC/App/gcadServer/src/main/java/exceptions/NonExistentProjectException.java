package exceptions;

public class NonExistentProjectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -326725099046408291L;

	public NonExistentProjectException(String message) {
		super(message);
	}

}
