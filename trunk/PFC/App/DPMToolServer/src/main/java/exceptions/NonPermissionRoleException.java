package exceptions;

public class NonPermissionRoleException extends Exception {

	private static final long serialVersionUID = -1978853635760699794L;

	public NonPermissionRoleException (String message) {
		super(message);
	}
}
