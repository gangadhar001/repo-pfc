package exceptions;

import internationalization.AppInternationalization;

public class NoProposalsException extends Exception {

	private static final long serialVersionUID = -1922112666647136171L;

	public NoProposalsException() {
		AppInternationalization.getString("Exception.NoProposalsException");
	}
	
}
