package model.business.control;

import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.IResources;
import model.business.knowledge.Language;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/** 
 * TODO: This class represents a controller to retrieve available languages and update the current language of the tool
 */
public class LanguagesController {

	@SuppressWarnings("unchecked")
	// Retrieve available languages from XML file
	public static ArrayList<Language> getLanguages() throws ConfigurationException {
		ArrayList<Language> result = new ArrayList<Language>();
		
		XMLConfiguration config;	    
		List<String> languagesName;
		List<String> languagesCode;
		
	    config = new XMLConfiguration(LanguagesController.class.getClassLoader().getResource(IResources.XML_LANGUAGES));
	    
	    // Take defined languages in that XML file (returns a list)
	    languagesName = config.getList("Language.name");		    		
	    languagesCode = config.getList("Language.code");
	    for (int i=0; i<languagesName.size(); i++){
	    	result.add(new Language(languagesName.get(i), languagesCode.get(i)));
	    }
	    return result;
	}

}
