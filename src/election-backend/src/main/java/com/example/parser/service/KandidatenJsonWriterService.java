package com.example.parser.service;

import com.example.parser.model.kandidatenlijst.KandidatenResult;
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

    /**
     * Verwerkt kandidatenlijsten en schrijft de resultaten naar een JSON-bestand.
     */
    public void writeKandidatenResultsToJson() {
        List<File> files = kandidatenlijstenFileProcessor.getKandidatenlijstenFiles();

        if (files.isEmpty()) {
            logger.warn("Geen kandidatenlijsten bestanden gevonden.");
            return;
        }

        List<KandidatenResult> kandidatenResults = parseKandidatenFiles(files);

        if (kandidatenResults.isEmpty()) {
            logger.warn("Geen data gevonden in de kandidatenlijsten.");
            return;
        }

        writeJsonToFile(kandidatenResults);
    }

    /**
     * Parseert de kandidatenlijsten bestanden naar objecten van type KandidatenResult.
     *
     * @param files Lijst van XML-bestanden.
     * @return Geparste resultaten.
     */
    private List<KandidatenResult> parseKandidatenFiles(List<File> files) {
        List<KandidatenResult> results = new ArrayList<>();

        for (File file : files) {
            try {
                JAXBContext context = JAXBContext.newInstance(KandidatenResult.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                KandidatenResult result = (KandidatenResult) unmarshaller.unmarshal(file);

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

    /**
     * Schrijft de geparste resultaten naar een JSON-bestand.
     *
     * @param results Lijst van geparste resultaten.
     */
    private void writeJsonToFile(List<KandidatenResult> results) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(JSON_OUTPUT_PATH);
            // kleiner maken van de JSON-bestand
            objectMapper.writeValue(jsonFile, results);
            logger.info("Kandidatenlijsten resultaten geschreven naar JSON-bestand: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fout bij het schrijven van de kandidatenlijsten naar JSON-bestand.", e);
        }
    }
}
