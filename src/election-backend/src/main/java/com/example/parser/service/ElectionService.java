package com.example.parser.service;

import com.example.parser.election.ElectionData;
import com.example.parser.election.Contest;
import com.example.parser.election.Candidate;
import com.example.parser.election.Party;
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
        sampleElection.setTransactionId("1");
        sampleElection.setElectionName("Tweede Kamer der Staten-Generaal 2023");
        sampleElection.setElectionDate("2023-11-22");
        sampleElection.setTotalVotes(1500000);

        List<Contest> contests = new ArrayList<>();

        Contest contest1 = new Contest();
        contest1.setContestIdentifier("alle");
        Party party1 = new Party();
        party1.setName("VVD");
        party1.setTotalVotes(1589519);

        List<Candidate> candidates1 = new ArrayList<>();
        candidates1.add(new Candidate("YeşilgözD", 1356883));
        candidates1.add(new Candidate("HermansSTM", 62320));
        candidates1.add(new Candidate("BeckerB", 14597));

        contest1.setCandidates(candidates1);
        contests.add(contest1);

        Contest contest2 = new Contest();
        contest2.setContestIdentifier("alle");
        Party party2 = new Party();
        party2.setName("D66");
        party2.setTotalVotes(656292);

        List<Candidate> candidates2 = new ArrayList<>();
        candidates2.add(new Candidate("JettenRAA", 437371));
        candidates2.add(new Candidate("PaternotteJM", 19645));

        contest2.setCandidates(candidates2);
        contests.add(contest2);

        Contest contest3 = new Contest();
        contest3.setContestIdentifier("alle");
        Party party3 = new Party();
        party3.setName("GroenLinks");
        party3.setTotalVotes(500000);

        List<Candidate> candidates3 = new ArrayList<>();
        candidates3.add(new Candidate("KleinL", 120000));
        candidates3.add(new Candidate("HagenKB", 60000));

        contest3.setCandidates(candidates3);
        contests.add(contest3);

        sampleElection.setContests(contests);
        electionDataList.add(sampleElection);

        System.out.println("Parsed Election Data:");
        for (ElectionData election : electionDataList) {
            System.out.println("Transaction ID: " + election.getTransactionId());
            System.out.println("Election Name: " + election.getElectionName());
            System.out.println("Election Date: " + election.getElectionDate());
            System.out.println("Total Votes: " + election.getTotalVotes());
            for (Contest contest : election.getContests()) {
                System.out.println("Contest Identifier: " + contest.getContestIdentifier());
                for (Candidate candidate : contest.getCandidates()) {
                    System.out.println("Candidate Name: " + candidate.getName());
                    System.out.println("Votes: " + candidate.getVotes());
                    System.out.println("Valid Votes: " + candidate.getValidVotes());
                }
            }
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
