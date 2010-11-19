package gcad.internationalization;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class BundleInternationalization {

	private BundleInternationalization() {
	}

	public static String getString(String key) {
		try {
			// It indicates the package which contains the "properties" file where it defines the names to be internationalized
			ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("gcad.internationalization.application");
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
