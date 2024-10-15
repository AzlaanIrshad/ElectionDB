package main.java.eu.election.api;

import eu.election.api.util.XmlLoader;
import eu.election.api.util.CandidateListParser;
import entity.Candidate;
import org.w3c.dom.Document;

import java.util.List;

public class ElectionDataApp {
    public static void main(String[] args) {
        // 1. Load the XML file
        String filePath = "C:/Users/Ersin/Desktop/HVA/yaadaasuuwii50/src/election-backend/src/main/resources/Kandidatenlijsten_TK2023_Amsterdam.eml.xml";
        XmlLoader loader = new XmlLoader(filePath);
        Document doc = loader.load();

        // 2. Parse the candidates from the XML document
        CandidateListParser parser = new CandidateListParser();
        List<Candidate> candidates = parser.parse(doc);

        // 3. Process the candidates (e.g., print to console)
        for (Candidate candidate : candidates) {
            System.out.println(candidate);
        }
    }
}
