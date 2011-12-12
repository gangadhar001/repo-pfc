/**
 * Class used to obtain geographic coordinates from an address. To do so, uses the Web Service "Yahoo! PlaceFinder"
 */
public class GeoCoder {

	// Yahoo! application ID
	private static final String APPID = "NzaqCw38";
	// Yahoo! Web Service base url
	private static final String BASE_URL =  "http://where.yahooapis.com/geocode";
			
	public static Coordinates getGeoCoordinates(Address address) throws NonExistentAddressException, WSResponseException, IOException, JDOMException {
		....
		
		String request = BASE_URL + "?q="+ad.toString()+"&appid="+APPID;
		url = new URL(request);
		// Connect and get response stream from Web Service
		InputStream in = url.openStream();
		// The response is an XML
		SAXBuilder builder = new SAXBuilder();
		// Uses JDOM and XPath in order to parser Xml
		Document doc;
		doc = builder.build(in);
		// Get values from XML using XPath
		String status = ((Element) XPath.selectSingleNode(doc, "/ResultSet/Error")).
		getContent(0).getValue();
		if (!status.equals("0"))
			throw new WSResponseException();
			
		String found = ((Element) XPath.selectSingleNode(doc, "/ResultSet/Found")).getContent(0).getValue();
		if (found.equals("0"))
			throw new NonExistentAddressException(AppInternationalization.
					getString("AddressNotFound_Exception"));
			
		String latitude = ((Element) XPath.selectSingleNode(doc, "/ResultSet/Result/latitude")).getContent(0).getValue();
		String longitude = ((Element) XPath.selectSingleNode(doc, "/ResultSet/Result/longitude")).getContent(0).getValue();		
		in.close();
		coor = new Coordinates(latitude, longitude);		
		return coor;
	}	
}
