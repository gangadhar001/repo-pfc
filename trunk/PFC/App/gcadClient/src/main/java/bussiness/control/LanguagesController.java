package bussiness.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Language;

import org.dom4j.io.OutputFormat;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import resources.XMLUtilities;

/** 
 * TODO: This class represents a controller to retrieve available languages and update the current language of the tool
 */
public class LanguagesController {

	private static SAXBuilder builder = new SAXBuilder();
	private static Document doc;
	private static final String LANGUAGES_FILE = "configuration/languages/Languages.xml";
	
	// Retrieve available languages from XML file
	public static ArrayList<Language> getLanguages() throws JDOMException, IOException {
		ArrayList<Language> result = new ArrayList<Language>();
		
		// Uses JDOM and XPath in order to parser Xml
		doc = builder.build(LanguagesController.class.getClassLoader().getResource(LANGUAGES_FILE));
		// Get values from XML using XPath
		List<Element> nodes = XMLUtilities.selectNodes(doc, "//Language");
		
		for (int i = 0; i<nodes.size(); i++) {	
			// Take defined languages in that XML file (returns a list)
	    	result.add(new Language(nodes.get(i).getChildText("name"), nodes.get(i).getChildText("code")));
	    }
	    return result;
	}

	public static void setDefaultLanguage(Language language) throws JDOMException, IOException {
		// Uses JDOM and XPath in order to parser Xml
		doc = builder.build(LanguagesController.class.getClassLoader().getResource(LANGUAGES_FILE));
		// Set the new default language
		Element node = XMLUtilities.selectNode(doc, "//default[. = 'true']");
		node.setText("false");
		XMLUtilities.selectNodeFromValueChild(doc, "//code[. = '"+language.getCode()+"']", "default").setText("true");

		// Save changes
		XMLOutputter output = new XMLOutputter();
		FileWriter fw = new FileWriter(new File(LanguagesController.class.getClassLoader().getResource(LANGUAGES_FILE).getPath()));
		output.output(doc, fw);
		fw.close();
		
	}  
	
	public static Language getDefaultLanguage() throws JDOMException, IOException {
		
		// Uses JDOM and XPath in order to parser Xml
		doc = builder.build(LanguagesController.class.getClassLoader().getResource(LANGUAGES_FILE));
		Language l = new Language();
		// Get values from XML using XPath
		l.setName(XMLUtilities.selectNodeFromValueChild(doc, "//default[. = 'true']", "name").getValue());
		l.setCode(XMLUtilities.selectNodeFromValueChild(doc, "//default[. = 'true']", "code").getValue());
		return l;
	}
}
