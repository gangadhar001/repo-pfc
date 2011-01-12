package exceptions;

import internationalization.BundleInternationalization;

public class NoProposalsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1922112666647136171L;

	public NoProposalsException() {
		BundleInternationalization.getString("Exception.NoProposalsException");
	}
	
}
