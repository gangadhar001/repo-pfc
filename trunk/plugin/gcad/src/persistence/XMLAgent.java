package persistence;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class XMLAgent {

    public static <E> void marshal(String fileName, Class<E> c, Object o) throws JAXBException {
    
    JAXBContext jaxbContent = JAXBContext.newInstance(c);

    Marshaller marshaller = jaxbContent.createMarshaller();
    marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); 
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.marshal((E)o, new File (fileName));
    //marshaller.marshal((E)ob, System.out);
    }


    /*public static <E> E unmarshal (String filename, Class<E> c) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
            Object result = null;
            JAXBContext jc = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            try {
                    result = (E)unmarshaller.unmarshal(ResourceRetriever.getResourceAsStream(filename));
            } catch (JAXBException e) {
                    marshal(filename, c, c.newInstance());
            }
            return (E)unmarshaller.unmarshal(ResourceRetriever.getResourceAsStream(filename));
    }*/

}

