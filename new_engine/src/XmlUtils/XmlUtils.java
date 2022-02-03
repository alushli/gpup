package XmlUtils;

import newExceptions.XmlException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

public class XmlUtils {
    /* the function reade from xml file and return the root object */
    public static Object readFromXml(InputStream filePath, Object object) throws XmlException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object root = (Object) jaxbUnmarshaller.unmarshal(filePath);
            return root;
        } catch (JAXBException e) {
            return null;
        }
    }
}
