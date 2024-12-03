package com.example.parser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class KandidatenlijstenFileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(KandidatenlijstenFileProcessor.class);

    public List<File> getKandidatenlijstenFiles(int year) {
        List<File> files = new ArrayList<>();

        // 2023
        String directoryPath1 = "src/election-backend/src/main/resources/ElectionResults/" + year +
                "/Verkiezingsuitslag Tweede Kamer " + year + " (Deel 1)/Kandidatenlijsten";
        files.addAll(getFiles(directoryPath1, year));

        // 2021
        String directoryPath2 = "src/election-backend/src/main/resources/ElectionResults/" + year +
                "/EML_bestanden_TK" + year + "_deel_1";
        files.addAll(getFiles(directoryPath2, year));

        // 2017
        String directoryPath3 = "src/election-backend/src/main/resources/ElectionResults/" + year +
                "/CSB_uitslag";
        files.addAll(getFiles(directoryPath3, year));

        // 2012
        String directoryPath4 = "src/election-backend/src/main/resources/ElectionResults/" + year +
                "/EML_bestanden_TK2012";
        files.addAll(getFiles(directoryPath4, year));

        // 2010
        String directoryPath5 = "src/election-backend/src/main/resources/ElectionResults/" + year +
                "/EML_bestanden_TK2010";
        files.addAll(getFiles(directoryPath5, year));

        logger.info("Aantal kandidatenlijsten bestanden gevonden voor verwerking: {}", files.size());
        return files;
    }

    private List<File> getFiles(String directoryPath, int year) {
        List<File> files = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] matchedFiles = directory.listFiles((dir, name) ->
                    name.startsWith("Kandidatenlijsten_TK" + year + "_") && name.endsWith(".xml")
            );

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
}
