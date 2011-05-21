package exceptions;

public class AddressNotFound extends Exception {

	private static final long serialVersionUID = -1376169882651343468L;

	public AddressNotFound(String message) {
		super(message);
	}
}
