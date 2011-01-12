package persistence;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class XMLAgent {

    public static <E> void marshal(String fileName, Class<E> className, Object obj) throws JAXBException {
    
    JAXBContext jaxbContent = JAXBContext.newInstance(className);

    Marshaller marshaller = jaxbContent.createMarshaller();
    marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); 
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.marshal((E)obj, new File (fileName));
    
    }

}

