package exceptions;

public class NonExistentCompanyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2928405021756214794L;

	public NonExistentCompanyException(String message) {
		super(message);
	}

}
