package model.business.control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class used to encrypt user passwords
 */
public class PasswordUtilities {

	// REFERENCE: http://www.rgagnon.com/javadetails/java-0400.html
	public static String encryptPasswordSHA1(String password) throws NoSuchAlgorithmException {
		MessageDigest key;
		StringBuffer keyBuffer;
		byte[] keyBytes;
		int value;
		int i;
		
		// Generate key with SHA-1 algorithm
		key = java.security.MessageDigest.getInstance("SHA-1");
		key.reset();
		key.update(password.getBytes());
		keyBytes = key.digest();

		// Convert key to hexadecimal string
		keyBuffer = new StringBuffer(keyBytes.length * 2);
		for (i = 0; i < keyBytes.length; i++) {
			value = keyBytes[i] & 0xff;
			if (value < 16) {
				keyBuffer.append('0');
			}
			keyBuffer.append(Integer.toHexString(value));
		}

		return keyBuffer.toString();
	}	
}
