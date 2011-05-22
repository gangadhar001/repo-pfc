package exceptions;

import internationalization.AppInternationalization;

public class WSResponseError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136645202690994696L;

	public WSResponseError() {
		super(AppInternationalization.getString("WSResponse_Exception"));
	}
}
