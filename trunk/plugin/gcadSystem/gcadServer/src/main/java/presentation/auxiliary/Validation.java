package presentation.auxiliary;

import internationalization.BundleInternationalization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidIPException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidPortException;

/**
 * Static class that contains methods to check the validity of the fields in the UI windows.
 */
public class Validation {
	
	public static final int MINIMUM_PORT = 1;
	public static final int MAXIMUM_PORT = 65535;
	public static final int MAXIMUM_LENGTH = 255;
	// TODO: ponerla mas larga
	public static final int MINIMUM_PASSWORD_LENGTH = 4;
	
	public static void checkIPAddress(String ip) throws InvalidIPException {
		Pattern IPPattern;
		
		IPPattern = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
		
		if(ip.equals("")) {
			throw new InvalidIPException(BundleInternationalization.getString("AddressIP_Empty_Message"));
		} else if(!IPPattern.matcher(ip).matches()) {
			throw new InvalidIPException();
		}
	}
	
	public static void checkPort(String port) throws InvalidPortException {
		int portNumber;
	
		if(port.equals("")) {
			throw new InvalidPortException(BundleInternationalization.getString("Port_Empty_Message"));
		} else {
			try {
				portNumber = Integer.parseInt(port);
				if(portNumber < MINIMUM_PORT || portNumber > MAXIMUM_PORT) {
					throw new InvalidPortException(BundleInternationalization.getString("PortInvalid_Exception") + " ");
				}
			} catch(NumberFormatException ex) {
				throw new InvalidPortException();
			}
		}
	}
	
	public static void checkPassword(String password) throws InvalidPasswordException {
		Pattern passwordPattern;
	    Matcher matcher;
		boolean bValid = false;
		
		if(password.length() > MAXIMUM_LENGTH) {
			throw new InvalidPasswordException(BundleInternationalization.getString("InvalidPassword_Exception"));
		}

		if(password.length() >= MINIMUM_PASSWORD_LENGTH) {
			passwordPattern = Pattern.compile("[a-zA-Z0-9]+");
		    matcher = passwordPattern.matcher(password);
		    if(matcher.matches())
	    		bValid = true;
		}
		
	    if (!bValid)
	    	throw new InvalidPasswordException(BundleInternationalization.getString("FormatPassword_Exception") + " " + String.valueOf(MINIMUM_PASSWORD_LENGTH) + " " + BundleInternationalization.getString("Characters_Message"));
	}
	
}
