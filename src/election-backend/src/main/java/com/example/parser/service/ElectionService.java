package com.example.parser.service;

import com.example.parser.model.ElectionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ElectionService {

    private static final Logger logger = LoggerFactory.getLogger(ElectionService.class);
    private static final String[] SPECIFIED_XML_FILE_PATHS = {
            "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)/Totaaltelling_TK2023.eml.xml",
            "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)/Telling_TK2023_kieskring_Amsterdam.eml.xml"
    };
    private static final String DIRECTORY_PATH_DEEL_1 = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)";
    private static final String DIRECTORY_PATH_DEEL_2 = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 2)";
    private static final String DIRECTORY_PATH_DEEL_3 = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 3)";
    private static final String JSON_OUTPUT_PATH = "C:/Users/Ersin/Desktop/HVA/SM3-election/src/main/resources/election_results.json";

    public List<ElectionResult> parseXmlToJson() {
        logger.info("Starting parseXmlToJson method...");
        List<ElectionResult> results = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<ElectionResult>> futures = new ArrayList<>();

        for (String xmlFilePath : SPECIFIED_XML_FILE_PATHS) {
            futures.add(executor.submit(new XmlFileProcessor(xmlFilePath)));
        }

        processDirectory(DIRECTORY_PATH_DEEL_1, "Telling_TK2023_kieskring_", futures, executor);
        processDirectory(DIRECTORY_PATH_DEEL_2, "Telling_TK2023_gemeente_", futures, executor);
        processDirectory(DIRECTORY_PATH_DEEL_3, "Telling_TK2023_gemeente_", futures, executor);
        processDirectory(DIRECTORY_PATH_DEEL_1 + "/Kandidatenlijsten", "Kandidatenlijsten_TK2023_", futures, executor);

        for (Future<ElectionResult> future : futures) {
            try {
                ElectionResult result = future.get();
                if (result != null) {
                    results.add(result);
                }
            } catch (Exception e) {
                logger.error("Error processing XML file in thread", e);
            }
        }

        executor.shutdown();
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
                for (File xmlFile : xmlFiles) {
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
        try {
            logger.info("Writing parsed data to JSON file at: {}", JSON_OUTPUT_PATH);
            File jsonFile = new File(JSON_OUTPUT_PATH);
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
