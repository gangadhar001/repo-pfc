package presentation.auxiliary;

import java.util.regex.Pattern;

import exceptions.InvalidIPException;
import exceptions.InvalidPortException;

/**
 * TODO: Clase estática que contiene métodos para comprobar la validez
 * de los campos de las ventanas.
 */
public class Validation {
	
	public static final int PUERTO_MINIMO = 1;
	public static final int PUERTO_MAXIMO = 65535;
	
	public static void comprobarDireccionIP(String ip) throws InvalidIPException {
		Pattern patronIP;
		
		// Creamos un patrón que define las IPs válidas
		patronIP = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
		
		if(ip.equals("")) {
			throw new InvalidIPException("La dirección IP no puede ser nula.");
		} else if(!patronIP.matcher(ip).matches()) {
			throw new InvalidIPException("La dirección IP no tiene el formato correcto");
		}
	}
	
	public static void comprobarPuerto(String puerto) throws InvalidPortException {
		int numPuerto;
	
		if(puerto.equals("")) {
			throw new InvalidPortException("El puerto no puede ser nulo.");
		} else {
			try {
				numPuerto = Integer.parseInt(puerto);
				if(numPuerto < PUERTO_MINIMO || numPuerto > PUERTO_MAXIMO) {
					throw new InvalidPortException("El puerto debe ser un número comprendido entre " + PUERTO_MINIMO + " y " + PUERTO_MAXIMO + ".");
				}
			} catch(NumberFormatException ex) {
				throw new InvalidPortException("El puerto debe ser un número comprendido entre " + PUERTO_MINIMO + " y " + PUERTO_MAXIMO + ".");
			}
		}
	}
	
}
