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
        String directoryPath;

        if (year == 2021) {
            directoryPath = "src/election-backend/src/main/resources/ElectionResults/" + year + "/EML_bestanden_TK" + year + "_deel_1";
        } else {
             directoryPath = "src/election-backend/src/main/resources/ElectionResults/" + year + "/Verkiezingsuitslag Tweede Kamer " + year + " (Deel 1)";
        }

        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] matchedFiles = directory.listFiles((dir, name) -> {
                if (year == 2021) {
                    return name.startsWith("Telling_TK" + year + "_kieskring_") && name.endsWith(".eml.xml");
                } else {
                    return name.startsWith("Telling_TK" + year) && name.endsWith(".xml");
                }
            });

            if (matchedFiles != null) {
                for (File file : matchedFiles) {
                    if (file.getName().startsWith("Telling_TK" + year + "_kieskring_")) {
                        tellingFiles.add(file);
                        logger.info("Bestand toegevoegd voor verwerking: {}", file.getName());
                    }
                }
            }
        } else {
            logger.warn("Directory niet gevonden: {}", directoryPath);
        }

        // Log het aantal gevonden bestanden
        logger.info("Aantal tellingen bestanden gevonden voor verwerking: {}", tellingFiles.size());

        return tellingFiles;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
