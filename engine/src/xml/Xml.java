package xml;

import exceptions.XmlException;

import javax.xml.bind.*;
import java.io.File;

public class Xml {
    /* the function reade from xml file and return the root object */
    public static Object readFromXml(String filePath, Object object) throws XmlException {
        try {
            if(!filePath.endsWith(".xml"))
                throw new XmlException("the file must be xml file");
            File file = new File(filePath);
            if (!file.exists())
                throw new XmlException("can not find the file \"" + filePath + "\"");
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object root = (Object) jaxbUnmarshaller.unmarshal(file);
            return root;
        } catch (JAXBException e) {
            return null;
        }
    }

    /* the function reade from xml file and return the root object */
    public static void writeToXml(String filePath, Object object) {
        try {
            File file = new File(filePath + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
