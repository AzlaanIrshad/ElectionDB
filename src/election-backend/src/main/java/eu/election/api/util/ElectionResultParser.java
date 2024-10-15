package eu.election.api.util;

import entity.Candidate;
import entity.ElectionResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class ElectionResultParser {

    public List<ElectionResult> parse(Document doc, List<Candidate> candidates) {
        NodeList resultNodes = doc.getElementsByTagName("Resultaat");
        List<ElectionResult> results = new ArrayList<>();

        for (int i = 0; i < resultNodes.getLength(); i++) {
            Element resultElement = (Element) resultNodes.item(i);

            // Assuming the XML contains the candidate's name to identify them
            String candidateName = resultElement.getElementsByTagName("Naam").item(0).getTextContent();
            int votes = Integer.parseInt(resultElement.getElementsByTagName("Stemmen").item(0).getTextContent());

            // Find the corresponding candidate
            Candidate matchedCandidate = findCandidateByName(candidates, candidateName);
            if (matchedCandidate != null) {
                ElectionResult result = new ElectionResult(matchedCandidate, votes);
                results.add(result);
            }
        }

        return results;
    }

    private Candidate findCandidateByName(List<Candidate> candidates, String name) {
        for (Candidate candidate : candidates) {
            if (candidate.getName().equalsIgnoreCase(name)) {
                return candidate;
            }
        }
        return null;
    }
}
