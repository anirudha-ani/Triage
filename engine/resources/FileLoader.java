package engine.resources;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class FileLoader {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder;
    Document doc;
    String filePath;


    public FileLoader(String filePath) {
        try {
            docBuilder = factory.newDocumentBuilder();
            try {
                this.filePath = filePath;
                doc = docBuilder.parse(filePath);
                doc.getDocumentElement().normalize();
            } catch (Exception e) {
                System.out.println("Can't load file " + e);
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public String readElements(String tag)  {
        String desiredElement = null;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node level = (Node) xPath.compile("/data/" + tag).evaluate(doc, XPathConstants.NODE);
            desiredElement = level.getTextContent();
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
        return desiredElement;
    }

    public void modifyElements(String tag, String value) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node field = (Node) xPath.compile("/data/" + tag).evaluate(doc, XPathConstants.NODE);
            field.setTextContent(value);
            try {
                Transformer tf = TransformerFactory.newInstance().newTransformer();
                DOMSource domSource = new DOMSource(doc);
                StreamResult sr = new StreamResult(new File(this.filePath));
                try {
                    tf.transform(domSource, sr);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                }
            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            }
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

    }
}
