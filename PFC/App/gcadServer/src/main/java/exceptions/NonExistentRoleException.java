package exceptions;

public class NonExistentRoleException extends Exception {

	private static final long serialVersionUID = 5458533702652171770L;

	public NonExistentRoleException(String message) {
		super(message);
	}

	
}
