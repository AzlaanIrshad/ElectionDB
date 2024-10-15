package eu.election.api.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;

// Import the Candidate class from the entity package
import entity.Candidate;

public class CandidateListParser {

    public List<Candidate> parse(Document doc) {
        NodeList candidateNodes = doc.getElementsByTagName("Kandidaat");
        List<Candidate> candidates = new ArrayList<>();

        for (int i = 0; i < candidateNodes.getLength(); i++) {
            Element candidateElement = (Element) candidateNodes.item(i);
            String naam = candidateElement.getElementsByTagName("Naam").item(0).getTextContent();
            String partij = candidateElement.getElementsByTagName("Partij").item(0).getTextContent();

            Candidate candidate = new Candidate(naam, partij);
            candidates.add(candidate);
        }

        return candidates;
    }
}
