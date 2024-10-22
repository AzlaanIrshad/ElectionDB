package com.example.parser.service;

import com.example.parser.election.ElectionData;
import com.example.parser.election.Contest;
import com.example.parser.election.Candidate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


@Service
public class ElectionService {

    private final ObjectMapper objectMapper;

    @Autowired
    public ElectionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        init();
    }

    public void init() {
        try {
            List<ElectionData> electionDataList = parseElections();
            writeDataToJson(electionDataList);
        } catch (IOException e) {
            System.err.println("Error during initialization: " + e.getMessage());
        }
    }

    public List<ElectionData> parseElections() {
        List<ElectionData> electionDataList = new ArrayList<>();

        ElectionData sampleElection = new ElectionData();
        sampleElection.setTransactionId("12345");
        sampleElection.setElectionName("General Elections 2023");
        sampleElection.setElectionDate("2023-09-15");
        sampleElection.setTotalVotes(15000);

        electionDataList.add(sampleElection);

        System.out.println("Parsed Election Data:");
        for (ElectionData election : electionDataList) {
            System.out.println("Transaction ID: " + election.getTransactionId());
            System.out.println("Election Name: " + election.getElectionName());
            System.out.println("Election Date: " + election.getElectionDate());
            System.out.println("Total Votes: " + election.getTotalVotes());
            System.out.println("----------------------------");
        }

        parseXmlElectionData(electionDataList, "ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)/Totaaltelling_TK2023.eml.xml");

        return electionDataList;
    }

    private void parseXmlElectionData(List<ElectionData> electionDataList, String xmlFilePath) {
        try {
            URL resource = getClass().getClassLoader().getResource(xmlFilePath);
            if (resource == null) {
                System.err.println("XML file not found: " + xmlFilePath);
                return;
            }

            String decodedPath = URLDecoder.decode(resource.getFile(), "UTF-8");
            File xmlFile = new File(decodedPath);
            System.out.println("Accessing XML file at: " + decodedPath);
            System.out.println("File exists: " + xmlFile.exists());

            JAXBContext jaxbContext = JAXBContext.newInstance(ElectionData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ElectionData electionData = (ElectionData) unmarshaller.unmarshal(xmlFile);
            electionDataList.add(electionData);

            System.out.println("Parsed XML Election Data:");
            System.out.println("Transaction ID: " + electionData.getTransactionId());
            System.out.println("Election Name: " + electionData.getElectionName());
            System.out.println("Election Date: " + electionData.getElectionDate());

            for (Contest contest : electionData.getContests()) {
                System.out.println("Contest Identifier: " + contest.getContestIdentifier());
                for (Candidate candidate : contest.getCandidates()) {
                    System.out.println("Candidate Name: " + candidate.getName());
                    System.out.println("Votes: " + candidate.getVotes());
                    System.out.println("Valid Votes: " + candidate.getValidVotes());
                }
            }


            System.out.println("----------------------------");
        } catch (JAXBException | IOException e) {
            System.err.println("Error parsing XML file: " + e.getMessage());
        }
    }


    public void writeDataToJson(List<ElectionData> electionDataList) throws IOException {
        File jsonFile = new File("election_results.json");
        objectMapper.writeValue(jsonFile, electionDataList);
    }
}
