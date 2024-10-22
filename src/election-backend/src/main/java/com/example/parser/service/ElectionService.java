package com.example.parser.service;

import com.example.parser.election.ElectionData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
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

        parseXmlElectionData(electionDataList, "ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1).xml");

        return electionDataList;
    }

    private void parseXmlElectionData(List<ElectionData> electionDataList, String xmlFilePath) {
        try {
            File xmlFile = new File(getClass().getClassLoader().getResource(xmlFilePath).getFile());
            JAXBContext jaxbContext = JAXBContext.newInstance(ElectionData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ElectionData electionData = (ElectionData) unmarshaller.unmarshal(xmlFile);
            electionDataList.add(electionData);

            System.out.println("Parsed XML Election Data:");
            System.out.println("Transaction ID: " + electionData.getTransactionId());
            System.out.println("Election Name: " + electionData.getElectionName());
            System.out.println("Election Date: " + electionData.getElectionDate());
            System.out.println("Total Votes: " + electionData.getTotalVotes());
            System.out.println("----------------------------");
        } catch (JAXBException | NullPointerException e) {
            System.err.println("Error parsing XML file: " + e.getMessage());
        }
    }

    public void writeDataToJson(List<ElectionData> electionDataList) throws IOException {
        File jsonFile = new File("election_results.json");
        objectMapper.writeValue(jsonFile, electionDataList);
    }
}
