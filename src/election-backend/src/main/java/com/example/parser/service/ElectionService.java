package com.example.parser.service;

import com.example.parser.model.tellingen.ElectionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class ElectionService {

    private static final Logger logger = LoggerFactory.getLogger(ElectionService.class);

    @Autowired
    private TellingFileProcessor tellingFileProcessor;

    @Autowired
    private KandidatenJsonWriterService kandidatenJsonWriterService;

    @Autowired
    private TellingenJsonWriterService tellingenJsonWriterService;

    public void parseXmlFilesToJson(int year) {
        logger.info("Starten van het XML naar JSON verwerkingsproces voor jaar {}", year);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<ElectionResult>> futures = new ArrayList<>();

        futures.addAll(tellingFileProcessor.processFiles(executor, year));

        // Ophalen en loggen van resultaten
        List<ElectionResult> results = collectResults(futures);
        logger.info("Aantal succesvol verwerkte tellingen bestanden: {}", results.size());

        // Afsluiten van de executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // tellingen naar JSON-bestand
        tellingenJsonWriterService.writeTellingResultsToJson(year);

        // kandidatenlijsten naar JSON-bestand
        kandidatenJsonWriterService.writeKandidatenResultsToJson(year);
    }

    private List<ElectionResult> collectResults(List<Future<ElectionResult>> futures) {
        List<ElectionResult> results = new ArrayList<>();
        for (Future<ElectionResult> future : futures) {
            try {
                ElectionResult result = future.get();
                if (result != null) {
                    results.add(result);
                }
            } catch (Exception e) {
                logger.error("Fout bij het ophalen van resultaat uit future.", e);
            }
        }
        return results;
    }
}
