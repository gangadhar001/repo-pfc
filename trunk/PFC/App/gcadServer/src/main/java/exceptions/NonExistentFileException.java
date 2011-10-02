package exceptions;

public class NonExistentFileException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3892842819152034921L;

	public NonExistentFileException(String msg) {
		super(msg);
	}
}
