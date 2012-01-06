package exceptions;

public class InvalidIPException extends Exception {

	private static final long serialVersionUID = -1376169882651343468L;

	public InvalidIPException(String message) {
		super(message);
	}
}
