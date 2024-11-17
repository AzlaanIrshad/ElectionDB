package com.example.parser.service;

import com.example.parser.model.tellingen.Selection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TellingFileProcessor extends FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(TellingFileProcessor.class);
    private static final String DIRECTORY_PATH = "src/election-backend/src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)";
    private static final String FILE_PREFIX = "Telling_TK2023_";

    @Override
    protected List<File> getFiles() {
        List<File> files = new ArrayList<>();
        File directory = new File(DIRECTORY_PATH);

        if (directory.exists() && directory.isDirectory()) {
            File[] matchedFiles = directory.listFiles((dir, name) ->
                    name.startsWith(FILE_PREFIX) && name.endsWith(".xml")
            );
            if (matchedFiles != null) {
                for (File file : matchedFiles) {
                    files.add(file);
                    logger.info("Bestand toegevoegd voor verwerking: {}", file.getName());
                }
            }
        } else {
            logger.warn("Directory niet gevonden: {}", DIRECTORY_PATH);
        }

        logger.info("Aantal gevonden bestanden voor verwerking: {}", files.size());
        return files;
    }

    public void logPartiesWithCandidates() {
        List<File> files = getFiles();
        List<Selection> selections = parseSelectionsFromFiles(files);
        StringBuilder output = new StringBuilder("Partijen en kandidaten met geldige stemmen:\n");

        for (Selection selection : selections) {
            if (selection.getCandidate() != null) {
                String candidateId = selection.getCandidate().getCandidateIdentifier().getId();
                int validVotes = selection.getValidVotes();
                output.append(String.format("Kandidaat ID: %s, Geldige stemmen: %d\n", candidateId, validVotes));
            } else if (selection.getAffiliationIdentifier() != null) {
                String partyName = selection.getAffiliationIdentifier().getRegisteredName();
                output.append(String.format("Partij: %s\n", partyName));
            }
        }

        if (output.toString().trim().equals("Partijen en kandidaten met geldige stemmen:")) {
            output.append("Geen partijen en kandidaten gevonden.\n");
        }

        System.out.println(output.toString());
        logger.info(output.toString());
    }

    public List<Selection> getAllSelections() {
        List<File> files = getFiles();
        return parseSelectionsFromFiles(files);
    }

    public List<Selection> searchSelectionsByParty(String partyName) {
        return getAllSelections().stream()
                .filter(selection -> selection.getAffiliationIdentifier() != null
                        && partyName.equalsIgnoreCase(selection.getAffiliationIdentifier().getRegisteredName()))
                .collect(Collectors.toList());
    }

    private List<Selection> parseSelectionsFromFiles(List<File> files) {
        List<Selection> selections = new ArrayList<>();

        for (File file : files) {
            Selection selection = parseSelection(file);
            if (selection != null) {
                selections.add(selection);
                logger.info("Gevonden Selection: {}", selection);
            }
        }

        return selections;
    }

    private Selection parseSelection(File file) {
        // Voeg hier de XML parsing-logica toe om Selection-objecten uit het bestand te halen
        return null; // Placeholder voor daadwerkelijke parsing logica
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
