package com.example.parser.service;

import com.example.parser.model.gemeente.GemeenteResult;
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
public class GemeenteJsonWriterService {

    private static final Logger logger = LoggerFactory.getLogger(GemeenteJsonWriterService.class);

    @Autowired
    private GemeenteFileProcessor gemeenteFileProcessor;

    public void writeGemeenteResultsToJson(int year) {
        String jsonOutputPath = "src/election-backend/src/main/resources/ParsedJson/" + year + "/gemeente_results.json";

        List<File> files = gemeenteFileProcessor.getFiles(year);

        if (files.isEmpty()) {
            logger.warn("Geen gemeente bestanden gevonden voor jaar {}.", year);
            return;
        }

        // Gebruik van een ExecutorService voor multi-threaded verwerking
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<GemeenteResult>> futures = new ArrayList<>();

        // Voeg taken toe aan de executor
        for (File file : files) {
            futures.add(executor.submit(() -> parseGemeenteFile(file)));
        }

        List<GemeenteResult> gemeenteResults = collectResults(futures);

        if (gemeenteResults.isEmpty()) {
            logger.warn("Geen data gevonden in de gemeente bestanden.");
        } else {
            writeJsonToFile(gemeenteResults, jsonOutputPath);
        }

        // Shut down de executor service
        executor.shutdown();
    }

    private GemeenteResult parseGemeenteFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(GemeenteResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            GemeenteResult result = (GemeenteResult) unmarshaller.unmarshal(file);
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

    private List<GemeenteResult> collectResults(List<Future<GemeenteResult>> futures) {
        List<GemeenteResult> results = new ArrayList<>();
        for (Future<GemeenteResult> future : futures) {
            try {
                GemeenteResult result = future.get();
                if (result != null) {
                    results.add(result);
                }
            } catch (Exception e) {
                logger.error("Fout bij het ophalen van resultaat uit future.", e);
            }
        }
        return results;
    }

    private void writeJsonToFile(List<GemeenteResult> results, String jsonOutputPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File jsonFile = new File(jsonOutputPath);
            jsonFile.getParentFile().mkdirs();
            objectMapper.writeValue(jsonFile, results);
            logger.info("Gemeente resultaten geschreven naar JSON-bestand: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fout bij het schrijven van de gemeente naar JSON-bestand.", e);
        }
    }
}
