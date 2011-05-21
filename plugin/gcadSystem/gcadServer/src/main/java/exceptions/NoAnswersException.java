package exceptions;

import internationalization.AppInternationalization;

public class NoAnswersException extends Exception {

	private static final long serialVersionUID = -1922112666647136171L;

	public NoAnswersException() {
		AppInternationalization.getString("Exception.NoAnswersException");
	}
	
}
