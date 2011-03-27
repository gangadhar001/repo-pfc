package exceptions;

public class NoAvailableLanguagesException extends Exception {

	private static final long serialVersionUID = -1376169882651343468L;

	public NoAvailableLanguagesException(String message) {
		super(message);
	}
}