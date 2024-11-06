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

@Service
public class ElectionService {

    private static final Logger logger = LoggerFactory.getLogger(ElectionService.class);
    private static final String XML_FILE_PATH = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)/Totaaltelling_TK2023.eml.xml";
    private static final String JSON_OUTPUT_PATH = "C:/Users/Ersin/Desktop/HVA/SM3-election/src/main/resources/election_results.json";

    public ElectionResult parseXmlToJson() {
        logger.info("Starting parseXmlToJson method...");

        try {
            logger.info("Checking if XML file exists at: {}", XML_FILE_PATH);
            File xmlFile = new File(XML_FILE_PATH);
            if (!xmlFile.exists()) {
                logger.error("XML file not found at specified path: {}", XML_FILE_PATH);
                return null;
            }
            logger.info("XML file found. File name: {}, File size: {} bytes", xmlFile.getName(), xmlFile.length());

            logger.info("Initializing JAXB context for ElectionResult class.");
            JAXBContext context = JAXBContext.newInstance(ElectionResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            logger.debug("JAXB Unmarshaller created successfully.");

            logger.info("Beginning unmarshalling process for XML file.");
            ElectionResult result = (ElectionResult) unmarshaller.unmarshal(xmlFile);
            logger.info("Unmarshalling completed successfully. Data successfully loaded from XML.");

            if (result == null) {
                logger.warn("Unmarshalled data is null. Please check XML structure and namespaces.");
            } else {
                logger.info("Data loaded successfully from XML. Proceeding to JSON conversion.");
                writeJsonToFile(result);
            }

            return result;

        } catch (JAXBException e) {
            logger.error("Error occurred during XML parsing.", e);
            return null;
        }
    }

    private void writeJsonToFile(ElectionResult result) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            logger.info("Attempting to write parsed data to JSON file at: {}", JSON_OUTPUT_PATH);
            File jsonFile = new File(JSON_OUTPUT_PATH);

            objectMapper.writeValue(jsonFile, result);
            logger.info("Data successfully written to JSON file.");
            logger.info("JSON file created. File name: {}, File path: {}, File size: {} bytes",
                    jsonFile.getName(), jsonFile.getAbsolutePath(), jsonFile.length());

        } catch (IOException e) {
            logger.error("Error occurred while writing data to JSON file.", e);
        }
    }
}
