package main.java.eu.election.api;

import eu.election.api.util.XmlLoader;
import eu.election.api.util.ElectionResultParser;
import eu.election.api.util.CandidateListParser;
import entity.Candidate;
import entity.ElectionResult;
import org.w3c.dom.Document;

import java.util.List;

public class ElectionResultApp {
    public static void main(String[] args) {
        // 1. Load the XML files
        String candidateFilePath = "C:/Users/Ersin/Desktop/HVA/yaadaasuuwii50/src/election-backend/src/main/resources/Kandidatenlijsten_TK2023_Amsterdam.eml.xml";
        String resultFilePath = "C:/Users/Ersin/Desktop/HVA/yaadaasuuwii50/src/election-backend/src/main/resources/Telling_TK2023_gemeente_Aa_en_Hunze.eml.xml";

        // Load candidate data
        XmlLoader candidateLoader = new XmlLoader(candidateFilePath);
        Document candidateDoc = candidateLoader.load();
        CandidateListParser candidateParser = new CandidateListParser();
        List<Candidate> candidates = candidateParser.parse(candidateDoc);

        // Load election result data
        XmlLoader resultLoader = new XmlLoader(resultFilePath);
        Document resultDoc = resultLoader.load();
        ElectionResultParser resultParser = new ElectionResultParser();
        List<ElectionResult> electionResults = resultParser.parse(resultDoc, candidates);

        // Process the results (e.g., print to console)
        for (ElectionResult result : electionResults) {
            System.out.println(result);
        }
    }
}