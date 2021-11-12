package xml;

import exceptions.XmlException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Xml {
    /* the function reade from xml file and return the root object */
    public static Object readFromXml(String filePath, Object object) throws XmlException {
        try {
            if(!filePath.substring(filePath.length() - 4).equals(".xml"))
                throw new XmlException("the file must be xml file");
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object root = (Object) jaxbUnmarshaller.unmarshal(file);
            return root;
        } catch (JAXBException e) {
            throw new XmlException("can not find the file \"" + filePath + "\"");
        }
    }



}
