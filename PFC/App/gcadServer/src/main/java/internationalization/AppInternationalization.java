package internationalization;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


import resources.LanguagesUtilities;

/**
 * This class is used to internationalized the server
 */
public class AppInternationalization {

    public static String getString(String key) {
	    try {
	    	// Retrieve the current language  
	    	String languageCode = LanguagesUtilities.getDefaultLanguage().getCode();
	    	// It indicates the package which contains the "properties" file where it defines the names to be internationalized
	        ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("internationalization.server_" + languageCode);
	        return RESOURCE_BUNDLE.getString(key);
	    } catch (MissingResourceException e) {
	            return '!' + key + '!';
	    } catch (Exception e) {
	    	return '!' + key + '!';
		} 
    }
}