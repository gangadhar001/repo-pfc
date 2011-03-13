package internationalization;

import gcad.Activator;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class is used to internationalize the plug-in
 */
public class BundleInternationalization {

    public static String getString(String key) {
	    try {
	    	// Retrieve the current plug-in language  
	    	String languageCode = Activator.currentLanguage;
	    	// It indicates the package which contains the "properties" file where it defines the names to be internationalized
	        ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("internationalization.application_"+languageCode);
	        return RESOURCE_BUNDLE.getString(key);
	    } catch (MissingResourceException e) {
	            return '!' + key + '!';
	    }
    }
}