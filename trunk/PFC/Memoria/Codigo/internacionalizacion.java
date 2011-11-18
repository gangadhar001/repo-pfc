public class ApplicationInternationalization {

    public static String getString(String key) {
	    try {
	    	// Retrieve the current language  
	    	String languageCode = LanguagesUtilities.getDefaultLanguage().getCode();
	    	// It indicates the package which contains the "properties" file where it defines the names to be internationalized
	        ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("internationalization.app_" + languageCode);
	        return RESOURCE_BUNDLE.getString(key);
	    } catch (MissingResourceException e) {
	            return '!' + key + '!';
	    } catch (JDOMException e) {
	    	return '!' + key + '!';
		} catch (IOException e) {
			return '!' + key + '!';
		}
    }
}