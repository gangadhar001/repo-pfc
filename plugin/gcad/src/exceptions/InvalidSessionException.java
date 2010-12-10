package exceptions;

public class InvalidSessionException extends Exception {

	private static final long serialVersionUID = 3569145770013270856L;

	public InvalidSessionException (String message) {
		super(message);
	}
}