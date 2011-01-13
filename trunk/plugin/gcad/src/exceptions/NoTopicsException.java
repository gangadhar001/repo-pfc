package exceptions;

import internationalization.BundleInternationalization;

public class NoTopicsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3408382798794505114L;

	public NoTopicsException() {
		BundleInternationalization.getString("Exception.NoTopicsException");
	}
}
