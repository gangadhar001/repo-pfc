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