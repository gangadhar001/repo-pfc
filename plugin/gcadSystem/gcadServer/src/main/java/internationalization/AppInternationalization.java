package internationalization;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class is used to internationalize the server
 */
public class AppInternationalization {

    public static String getString(String key) {
	    try {
	    	// TODO: Retrieve the current language  
	    	// It indicates the package which contains the "properties" file where it defines the names to be internationalized
	        ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("internationalization.server");
	        return RESOURCE_BUNDLE.getString(key);
	    } catch (MissingResourceException e) {
	            return '!' + key + '!';
	    }
    }
}