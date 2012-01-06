package exceptions;

import internationalization.AppInternationalization;

public class WSResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136645202690994696L;

	public WSResponseException() {
		super(AppInternationalization.getString("WSResponse_Exception"));
	}
}
