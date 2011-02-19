package exceptions;

public class NonPermissionRole extends Exception {

	private static final long serialVersionUID = -1978853635760699794L;

	public NonPermissionRole (String message) {
		super(message);
	}
}
