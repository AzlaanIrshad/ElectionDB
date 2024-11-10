package com.example.parser.service;

import com.example.parser.model.election.ElectionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ElectionService {

    private static final Logger logger = LoggerFactory.getLogger(ElectionService.class);

    @Autowired
    private TellingFileProcessor tellingFileProcessor;

    @Autowired
    private GemeenteTellingFileProcessor gemeenteTellingFileProcessor;

    @Autowired
    private KandidatenlijstenFileProcessor kandidatenlijstenFileProcessor;

    @Autowired
    private JsonWriterService jsonWriterService;

    public void parseXmlFilesToJson() {
        logger.info("Starten van het XML naar JSON verwerkingsproces...");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<ElectionResult>> futures = new ArrayList<>();

        futures.addAll(tellingFileProcessor.processFiles(executor));
        futures.addAll(gemeenteTellingFileProcessor.processFiles(executor));
        futures.addAll(kandidatenlijstenFileProcessor.processFiles(executor));

        List<ElectionResult> results = collectResults(futures);
        executor.shutdown();

        jsonWriterService.writeJsonToFile(results);
    }

    private List<ElectionResult> collectResults(List<Future<ElectionResult>> futures) {
        List<ElectionResult> results = new ArrayList<>();
        for (Future<ElectionResult> future : futures) {
            try {
                ElectionResult result = future.get();
                if (result != null) results.add(result);
            } catch (Exception e) {
                logger.error("Fout bij het ophalen van resultaat uit future.", e);
            }
        }
        return results;
    }
}
