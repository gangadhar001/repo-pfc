package resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class XMLUtilities {

	// Marshal domain class into XML file, using JAXB
	public static <E> ByteArrayOutputStream marshal(Class<E> className, Object obj) throws JAXBException {
    
	    JAXBContext jaxbContent = JAXBContext.newInstance(className);
	
	    Marshaller marshaller = jaxbContent.createMarshaller();
	    marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); 
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    marshaller.marshal(obj, baos);
	    return baos;
    }
    
    /*** Methods used to read and parse XML, using JDOM and XPath  ***/
    public static Document readXML(URL url) throws JDOMException, IOException {
    	SAXBuilder builder = new SAXBuilder();
		return builder.build(url);
    }

	@SuppressWarnings("unchecked")
	public static List<Attribute> selectAttributes(Document docCharts, String xpath) throws JDOMException {
		return XPath.selectNodes(docCharts, xpath);
	}

	public static String selectString(Document docCharts, String xpath) throws JDOMException {
		return  XPath.selectNodes(docCharts, xpath).get(0).toString();
	}

	@SuppressWarnings("unchecked")
	public static List<Element> selectNodes(Document docCharts, String xpath) throws JDOMException {
		return XPath.selectNodes(docCharts, xpath);
	}
	
	public static Element selectNode(Document docCharts, String xpath) throws JDOMException {
		return (Element) XPath.selectSingleNode(docCharts, xpath);
	}

	public static Element selectNodeFromValueChild(Document docChartsConfiguration, String xpath, String nodeName) throws JDOMException {
		return ((Element)((Element)XPath.selectSingleNode(docChartsConfiguration, xpath)).getParent()).getChild(nodeName);
	}

}

