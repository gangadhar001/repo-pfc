package exceptions;

public class IncorrectEmployeeException extends Exception {

	private static final long serialVersionUID = -1376169882651343468L;

	public IncorrectEmployeeException(String message) {
		super(message);
	}
}