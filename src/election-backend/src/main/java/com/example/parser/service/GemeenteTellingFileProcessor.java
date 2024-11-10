package com.example.parser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GemeenteTellingFileProcessor extends FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(GemeenteTellingFileProcessor.class);
    private static final String[] GEMEENTE_TELLING_DIRECTORIES = {
            "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 2)/Gemeente tellingen",
            "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 3)/Gemeente tellingen"
    };
    private static final String FILE_PREFIX = "Telling_TK2023_gemeente_";

    @Override
    protected List<File> getFiles() {
        List<File> allFiles = new ArrayList<>();
        for (String directoryPath : GEMEENTE_TELLING_DIRECTORIES) {
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles((dir, name) -> name.startsWith(FILE_PREFIX) && name.endsWith(".xml"));
                if (files != null) {
                    for (File file : files) {
                        allFiles.add(file);
                    }
                }
            } else {
                logger.warn("Directory niet gevonden: {}", directoryPath);
            }
        }
        return allFiles;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
