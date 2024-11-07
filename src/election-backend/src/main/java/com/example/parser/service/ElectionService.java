package com.example.parser.service;

import com.example.parser.model.election.ElectionResult;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ElectionService {

    private static final Logger logger = LoggerFactory.getLogger(ElectionService.class);
    private static final String[] SPECIFIED_XML_FILE_PATHS = {
            "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)/Totaaltelling_TK2023.eml.xml",
    };
    private static final String DIRECTORY_PATH_DEEL_1 = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)";
    private static final String DIRECTORY_PATH_DEEL_2 = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 2)/Gemeente tellingen";
    private static final String DIRECTORY_PATH_DEEL_3 = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 3)/Gemeente tellingen";
    private static final String DIRECTORY_PATH_KANDIDATENLIJSTEN = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)/Kandidatenlijsten";
    private static final String JSON_OUTPUT_PATH = "src/main/resources/election_results.json";

    public List<ElectionResult> parseXmlToJson() {
        logger.info("Starting parseXmlToJson method...");
        List<ElectionResult> results = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<ElectionResult>> futures = new ArrayList<>();

        try {
            // Process specified XML file paths
            for (String xmlFilePath : SPECIFIED_XML_FILE_PATHS) {
                futures.add(executor.submit(new XmlFileProcessor(xmlFilePath)));
            }
            // Process directories with specific prefixes
            processDirectory(DIRECTORY_PATH_DEEL_1, "Telling_TK2023_kieskring_", futures, executor);
            processDirectory(DIRECTORY_PATH_DEEL_2, "Telling_TK2023_gemeente_", futures, executor);
            processDirectory(DIRECTORY_PATH_DEEL_3, "Telling_TK2023_gemeente_", futures, executor);
            processDirectory(DIRECTORY_PATH_DEEL_1, "Verkiezingsdefinitie_TK2023_", futures, executor);
            processDirectory(DIRECTORY_PATH_KANDIDATENLIJSTEN, "Kandidatenlijsten_TK2023_", futures, executor);

            // Collect results from each task
            for (Future<ElectionResult> future : futures) {
                try {
                    ElectionResult result = future.get();
                    if (result != null) {
                        results.add(result);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Error processing XML file in thread", e);
                }
            }
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                        logger.error("Executor service did not terminate");
                }
            } catch (InterruptedException ex) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        if (!results.isEmpty()) {
            writeJsonToFile(results);
        }
        return results;
    }

    private void processDirectory(String directoryPath, String filePrefix, List<Future<ElectionResult>> futures, ExecutorService executor) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] xmlFiles = directory.listFiles((dir, name) -> name.startsWith(filePrefix) && name.endsWith(".eml.xml"));
            if (xmlFiles != null) {
                logger.info("Found {} files in directory: {}", xmlFiles.length, directoryPath);
                for (File xmlFile : xmlFiles) {
                    logger.info("File found: {}", xmlFile.getName());
                    futures.add(executor.submit(new XmlFileProcessor(xmlFile.getAbsolutePath())));
                }
            } else {
                logger.warn("No files matching the pattern found in directory: {}", directoryPath);
            }

        } else {
            logger.error("Directory not found or is not a directory: {}", directoryPath);
        }
    }

    private void writeJsonToFile(List<ElectionResult> results) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false); // Change to true for better readability

        try {
            logger.info("Writing parsed data to JSON file at: {}", JSON_OUTPUT_PATH);
            File jsonFile = Paths.get(JSON_OUTPUT_PATH).toFile();
            objectMapper.writeValue(jsonFile, results);
            logger.info("Data successfully written to JSON file.");
        } catch (IOException e) {
            logger.error("Error occurred while writing data to JSON file.", e);
        }
    }

    private class XmlFileProcessor implements Callable<ElectionResult> {
        private final String xmlFilePath;

        public XmlFileProcessor(String xmlFilePath) {
            this.xmlFilePath = xmlFilePath;
        }

        @Override
        public ElectionResult call() {
            try {
                logger.info("Processing file: {}", xmlFilePath);
                File xmlFile = new File(xmlFilePath);
                if (!xmlFile.exists()) {
                    logger.error("XML file not found at specified path: {}", xmlFilePath);
                    return null;
                }

                JAXBContext context = JAXBContext.newInstance(ElectionResult.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                return (ElectionResult) unmarshaller.unmarshal(xmlFile);
            } catch (JAXBException e) {
                logger.error("Error occurred during XML parsing for file: " + xmlFilePath, e);
                return null;
            }
        }
    }
}
