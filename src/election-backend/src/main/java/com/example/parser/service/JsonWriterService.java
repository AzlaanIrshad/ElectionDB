package com.example.parser.service;

import com.example.parser.model.election.ElectionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsonWriterService {

    private static final Logger logger = LoggerFactory.getLogger(JsonWriterService.class);
    private static final String JSON_OUTPUT_PATH = "src/election-backend/src/main/resources/election_results.json";
    //        C:\Users\Ersin\Desktop\HVA\SM3-election\src\main\resources
//        C:\Users\Ersin\Desktop\HVA\SM3-election\src\election-backend\src\main\resources

    public void writeJsonToFile(List<ElectionResult> results) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(JSON_OUTPUT_PATH);
            objectMapper.writeValue(jsonFile, results);
            logger.info("Resultaten geschreven naar JSON-bestand op: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fout bij het schrijven van resultaten naar JSON-bestand.", e);
        }
    }
}
