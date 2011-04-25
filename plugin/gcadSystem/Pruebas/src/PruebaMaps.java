import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

//REFERENCE: http://www.vogella.de/articles/JavaGeocoding/article.html#questions

public class PruebaMaps {

	
	public static void getCoordinates() {
		String baseUrl = "http://where.yahooapis.com/geocode"; 
		URL url;
		String request = baseUrl + "?q=paloma,ciudad+real,spain&appid=";//appid
		try {
			url = new URL(request);
			// Connect and get response stream from Web Service
			InputStream in = url.openStream();
			// The response is an XML
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(in);
			// Get values from XML using XPATH
			String status = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Error")).getContent(0).getValue();
			String found = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Found")).getContent(0).getValue();
			String latitude = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Result/latitude")).getContent(0).getValue();
			String longitude = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Result/longitude")).getContent(0).getValue();		
			in.close();
			System.out.println("Error " + status + " Found " +found + " Latitude " + latitude + " Longitude " + longitude);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		getCoordinates();
	}

}
