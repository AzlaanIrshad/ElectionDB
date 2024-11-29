package com.example.parser.service;

import com.example.parser.model.tellingen.ElectionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TellingenJsonWriterService {

    private static final Logger logger = LoggerFactory.getLogger(TellingenJsonWriterService.class);

    @Autowired
    private TellingFileProcessor tellingFileProcessor;

    public void writeTellingResultsToJson(int year) {
        String jsonOutputPath = "src/election-backend/src/main/resources/ParsedJson/" + year + "/tellingen_results.json";

        List<File> files = tellingFileProcessor.getFiles(year);

        if (files.isEmpty()) {
            logger.warn("Geen tellingen bestanden gevonden voor jaar {}.", year);
            return;
        }

        List<ElectionResult> tellingenResults = parseTellingFiles(files);

        if (tellingenResults.isEmpty()) {
            logger.warn("Geen data gevonden in de tellingen bestanden.");
            return;
        }

        writeJsonToFile(tellingenResults, jsonOutputPath);
    }

    private List<ElectionResult> parseTellingFiles(List<File> files) {
        List<ElectionResult> results = new ArrayList<>();

        for (File file : files) {
            try {
                JAXBContext context = JAXBContext.newInstance(ElectionResult.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ElectionResult result = (ElectionResult) unmarshaller.unmarshal(file);

                if (result != null) {
                    results.add(result);
                    logger.info("Geparste gegevens van bestand: {}", file.getName());
                } else {
                    logger.warn("Lege data gevonden in bestand: {}", file.getName());
                }
            } catch (Exception e) {
                logger.error("Fout bij het verwerken van bestand: {}", file.getName(), e);
            }
        }

        return results;
    }

    private void writeJsonToFile(List<ElectionResult> results, String jsonOutputPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File jsonFile = new File(jsonOutputPath);
            jsonFile.getParentFile().mkdirs();
            objectMapper.writeValue(jsonFile, results);
            logger.info("Tellingen resultaten geschreven naar JSON-bestand: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fout bij het schrijven van de tellingen naar JSON-bestand.", e);
        }
    }
}
