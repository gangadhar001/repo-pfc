package presentation.auxiliary;

import internationalization.AppInternationalization;

import java.util.regex.Pattern;

import exceptions.InvalidIPException;
import exceptions.InvalidPortException;

/**
 * Class used to validate the fields of the user interface
 * 
 */
public class Validation {
	
	public static final int MINIMUM_PORT = 1;
	public static final int MAXIMUM_PORT = 65535;
	
	public static void checkIP(String ip) throws InvalidIPException {
		Pattern IPPattern;
		
		// Creamos un patrón que define las IPs válidas
		IPPattern = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
		
		if(ip.equals("")) {
			throw new InvalidIPException(AppInternationalization.getString("ValidateIP"));
		} else if(!IPPattern.matcher(ip).matches()) {
			throw new InvalidIPException(AppInternationalization.getString("ValidateIP"));
		}
	}
	
	public static void checkPort(String port) throws InvalidPortException {
		int portNumber;
	
		if(port.equals("")) {
			throw new InvalidPortException(AppInternationalization.getString("ValidatePort"));
		}
		try {
			portNumber = Integer.parseInt(port);
			if(portNumber < MINIMUM_PORT || portNumber > MAXIMUM_PORT) {
				throw new InvalidPortException(AppInternationalization.getString("RangePort") + " [" + MINIMUM_PORT + "-" + MAXIMUM_PORT + "]");
			}
		} catch(NumberFormatException ex) {
			throw new InvalidPortException(AppInternationalization.getString("RangePort") + " [" + MINIMUM_PORT + "-" + MAXIMUM_PORT + "]");
		}
	}
	
}
