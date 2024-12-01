package com.example.parser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class TellingFileProcessor extends FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(TellingFileProcessor.class);

    @Override
    protected List<File> getFiles(int year) {
        List<File> tellingFiles = new ArrayList<>();

        // 2023/2021
        String directoryPath1 = year == 2021
                ? "src/election-backend/src/main/resources/ElectionResults/" + year + "/EML_bestanden_TK" + year + "_deel_1"
                : "src/election-backend/src/main/resources/ElectionResults/" + year + "/Verkiezingsuitslag Tweede Kamer " + year + " (Deel 1)";

        tellingFiles.addAll(getMatchingFiles(directoryPath1, year));

        // 2017
        String directoryPath2 = "src/election-backend/src/main/resources/ElectionResults/" + year + "/CSB_uitslag";
        tellingFiles.addAll(getMatchingFiles(directoryPath2, year));

        // Logt het aantal gevonden bestanden
        logger.info("Aantal tellingen bestanden gevonden voor verwerking: {}", tellingFiles.size());

        return tellingFiles;
    }

    private List<File> getMatchingFiles(String directoryPath, int year) {
        List<File> files = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] matchedFiles = directory.listFiles((dir, name) -> {
                if (year == 2021) {
                    return name.startsWith("Telling_TK" + year + "_kieskring_") && name.endsWith(".eml.xml");
                } else {
                    return (name.startsWith("Telling_TK" + year) || name.startsWith("Telling_")) && name.endsWith(".xml");
                }
            });

            if (matchedFiles != null) {
                for (File file : matchedFiles) {
                    files.add(file);
                    logger.info("Bestand toegevoegd voor verwerking: {}", file.getName());
                }
            }
        } else {
            logger.warn("Directory niet gevonden: {}", directoryPath);
        }

        return files;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
