package exceptions;

import internationalization.AppInternationalization;

public class NoTopicsException extends Exception {

	private static final long serialVersionUID = -3408382798794505114L;

	public NoTopicsException() {
		AppInternationalization.getString("Exception.NoTopicsException");
	}
}
