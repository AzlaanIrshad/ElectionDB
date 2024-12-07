package com.example.parser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GemeenteFileProcessor extends FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(GemeenteFileProcessor.class);

    @Override
    protected List<File> getFiles(int year) {
        List<File> gemeenteFiles = new ArrayList<>();

        String directoryPath2 = "src/election-backend/src/main/resources/ElectionResults/" + year + "/Verkiezingsuitslag Tweede Kamer " + year + " (Deel 2)/Gemeente tellingen";
        String directoryPath3 = "src/election-backend/src/main/resources/ElectionResults/" + year + "/Verkiezingsuitslag Tweede Kamer " + year + " (Deel 3)/Gemeente tellingen";

        gemeenteFiles.addAll(getMatchingFiles(directoryPath2, year));
        gemeenteFiles.addAll(getMatchingFiles(directoryPath3, year));

        logger.info("Totaal aantal gemeente bestanden gevonden voor verwerking: {}", gemeenteFiles.size());

        return gemeenteFiles;
    }

    private List<File> getMatchingFiles(String directoryPath, int year) {
        List<File> files = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] matchedFiles = directory.listFiles((dir, name) -> name.startsWith("Telling_TK" + year + "_gemeente_") && name.endsWith(".eml.xml"));
            if (matchedFiles != null) {
                for (File file : matchedFiles) {
                    files.add(file);
                    logger.info("Bestand toegevoegd voor verwerking: {}", file.getName());
                }
            }
        } else {
            logger.warn("Directory niet gevonden of is geen directory: {}", directoryPath);
        }

        return files;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}

