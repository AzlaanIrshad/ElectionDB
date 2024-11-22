package com.example.parser.service;

import com.example.parser.model.tellingen.ElectionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class KandidatenJsonWriterService {

    private static final Logger logger = LoggerFactory.getLogger(KandidatenJsonWriterService.class);
    private static final String JSON_OUTPUT_PATH = "src/election-backend/src/main/resources/kandidatenlijsten_results.json";

    @Autowired
    private KandidatenlijstenFileProcessor kandidatenlijstenFileProcessor;

    public void writeKandidatenResultsToJson() {
        List<File> files = kandidatenlijstenFileProcessor.getKandidatenlijstenFiles();

        if (files.isEmpty()) {
            logger.warn("Geen kandidatenlijsten bestanden gevonden.");
            return;
        }

        List<ElectionResult> kandidatenResults = parseKandidatenFiles(files);

        writeJsonToFile(kandidatenResults);
    }

    private List<ElectionResult> parseKandidatenFiles(List<File> files) {
        List<ElectionResult> results = new ArrayList<>();

        for (File file : files) {
            try {
                JAXBContext context = JAXBContext.newInstance(ElectionResult.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ElectionResult result = (ElectionResult) unmarshaller.unmarshal(file);
                results.add(result);
                logger.info("Geparste gegevens van bestand: {}", file.getName());
            } catch (Exception e) {
                logger.error("Fout bij het verwerken van bestand: {}", file.getName(), e);
            }
        }

        return results;
    }

    private void writeJsonToFile(List<ElectionResult> results) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(JSON_OUTPUT_PATH);
            objectMapper.writeValue(jsonFile, results);
            logger.info("Kandidatenlijsten resultaten geschreven naar JSON-bestand: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fout bij het schrijven van de kandidatenlijsten naar JSON-bestand.", e);
        }
    }
}
