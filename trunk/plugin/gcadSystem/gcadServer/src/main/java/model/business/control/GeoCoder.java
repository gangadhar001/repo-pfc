package model.business.control;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import model.business.knowledge.Address;
import model.business.knowledge.Coordinates;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * Class used to obtain geographic coordinates from an address. To do so, uses the Web Service "Yahoo! PlaceFinder"
 */
public class GeoCoder {

	// Yahoo! application ID
	private static final String APPID = "NzaqCw38";
	// Yahoo! Web Service base url
	private static final String BASE_URL =  "http://where.yahooapis.com/geocode";
			
	public static Coordinates getGeoCoordinates(Address address) {
		URL url;
		StringBuffer ad = new StringBuffer();
		ad.append(address.getStreet() != null ? address.getStreet() + "+" : "");
		ad.append(address.getCity() != null ? address.getCity() + "+" : "");
		ad.append(address.getZip() != null ? address.getZip() + "+" : "");
		ad.append(address.getCountry() != null ? address.getCountry() + "+" : "");
		if (ad.toString().endsWith("+"))
			ad.replace(ad.length()-1, ad.lastIndexOf("+"), "");
		
		String request = BASE_URL + "?q="+ad.toString()+"&appid="+APPID;
		url = new URL(request);
		// Connect and get response stream from Web Service
		InputStream in = url.openStream();
		// The response is an XML
		SAXBuilder builder = new SAXBuilder();
		// Uses JDOM and XPath in order to parser Xml
		Document doc = builder.build(in);
		// Get values from XML using XPath
		String status = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Error")).getContent(0).getValue();
		if (status.equals(0))
			// TODO ;
			throw new ;
		String found = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Found")).getContent(0).getValue();
		if (found.equals(0))
			// TODO ;
			throw new ;
		String latitude = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Result/latitude")).getContent(0).getValue();
		String longitude = ((Element) XPath.selectSingleNode(doc, "//ResultSet/Result/longitude")).getContent(0).getValue();		
		in.close();
		return new Coordinates(latitude, longitude);
	}
	
}
