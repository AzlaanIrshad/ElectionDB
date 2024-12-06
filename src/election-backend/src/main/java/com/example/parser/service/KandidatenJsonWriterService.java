package com.example.parser.service;

import com.example.parser.model.kandidatenlijst.KandidatenResult;
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
import java.util.concurrent.*;

@Service
public class KandidatenJsonWriterService {

    private static final Logger logger = LoggerFactory.getLogger(KandidatenJsonWriterService.class);

    @Autowired
    private KandidatenlijstenFileProcessor kandidatenlijstenFileProcessor;

    public void writeKandidatenResultsToJson(int year) {
        String jsonOutputPath = "src/election-backend/src/main/resources/ParsedJson/" + year + "/kandidatenlijsten_results.json";

        List<File> files = kandidatenlijstenFileProcessor.getKandidatenlijstenFiles(year);

        if (files.isEmpty()) {
            logger.warn("Geen kandidatenlijsten bestanden gevonden.");
            return;
        }

        // Gebruik van een ExecutorService voor multi-threaded verwerking
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<KandidatenResult>> futures = new ArrayList<>();

        // Voeg taken toe aan de executor voor elke file
        for (File file : files) {
            futures.add(executor.submit(() -> parseKandidatenFile(file)));
        }

        List<KandidatenResult> kandidatenResults = collectResults(futures);

        if (kandidatenResults.isEmpty()) {
            logger.warn("Geen data gevonden in de kandidatenlijsten.");
        } else {
            writeJsonToFile(kandidatenResults, jsonOutputPath);
        }

        // Shut down de executor service
        executor.shutdown();
    }

    private KandidatenResult parseKandidatenFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(KandidatenResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            KandidatenResult result = (KandidatenResult) unmarshaller.unmarshal(file);

            if (result != null) {
                logger.info("Geparste gegevens van bestand: {}", file.getName());
                return result;
            } else {
                logger.warn("Lege data gevonden in bestand: {}", file.getName());
                return null;
            }
        } catch (Exception e) {
            logger.error("Fout bij het verwerken van bestand: {}", file.getName(), e);
            return null;
        }
    }

    private List<KandidatenResult> collectResults(List<Future<KandidatenResult>> futures) {
        List<KandidatenResult> results = new ArrayList<>();
        for (Future<KandidatenResult> future : futures) {
            try {
                KandidatenResult result = future.get();
                if (result != null) {
                    results.add(result);
                }
            } catch (Exception e) {
                logger.error("Fout bij het ophalen van resultaat uit future.", e);
            }
        }
        return results;
    }

    private void writeJsonToFile(List<KandidatenResult> results, String jsonOutputPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File jsonFile = new File(jsonOutputPath);
            jsonFile.getParentFile().mkdirs();
            objectMapper.writeValue(jsonFile, results);
            logger.info("Kandidatenlijsten resultaten geschreven naar JSON-bestand: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fout bij het schrijven van de kandidatenlijsten naar JSON-bestand.", e);
        }
    }
}
