package exceptions;

import internationalization.BundleInternationalization;

public class NoAnswersException extends Exception {

	private static final long serialVersionUID = -1922112666647136171L;

	public NoAnswersException() {
		BundleInternationalization.getString("Exception.NoAnswersException");
	}
	
}
