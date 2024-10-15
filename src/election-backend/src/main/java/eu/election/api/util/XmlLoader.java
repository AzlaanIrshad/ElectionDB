package eu.election.api.util;

import org.w3c.dom.Document;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlLoader{
    private String path;

    public XmlLoader(String path){
        this.path = path;
    }

    public Document load() {
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading XML file: " + e.getMessage());
        }

        doc.getDocumentElement().normalize();
        return doc;
    }
}