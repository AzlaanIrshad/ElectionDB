package com.example.parser.service;

import com.example.parser.model.tellingen.ElectionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonWriterService {

    private static final Logger logger = LoggerFactory.getLogger(JsonWriterService.class);
    private static final String XML_INPUT_DIR = "src/election-backend/src/main/resources/ElectionResults";
    private static final String JSON_OUTPUT_PATH = "src/election-backend/src/main/resources/election_results.json";

    public void processAndWriteJson() {
        List<ElectionResult> combinedResults = new ArrayList<>();
        File directory = new File(XML_INPUT_DIR);

        if (!directory.exists() || !directory.isDirectory()) {
            logger.error("Input directory {} bestaat niet of is geen map.", XML_INPUT_DIR);
            return;
        }

        File[] xmlFiles = directory.listFiles((dir, name) -> name.endsWith(".xml"));
        if (xmlFiles == null || xmlFiles.length == 0) {
            logger.error("Geen XML-bestanden gevonden in directory {}", XML_INPUT_DIR);
            return;
        }

        writeJsonToFile(combinedResults);
    }

    void writeJsonToFile(List<ElectionResult> results) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(JSON_OUTPUT_PATH);
            objectMapper.writeValue(jsonFile, results);
            logger.info("Gecombineerde resultaten geschreven naar JSON-bestand: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fout bij het schrijven van resultaten naar JSON-bestand.", e);
        }
    }
}
