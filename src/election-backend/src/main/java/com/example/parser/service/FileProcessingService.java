package com.example.parser.service;

import com.example.parser.model.tellingen.ElectionResult;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.ArrayList;

public abstract class FileProcessingService {

    protected abstract List<File> getFiles();
    protected abstract Logger getLogger();

    public List<Future<ElectionResult>> processFiles(ExecutorService executor) {
        List<Future<ElectionResult>> futures = new ArrayList<>();
        List<File> files = getFiles();

        for (File file : files) {
            futures.add(executor.submit(() -> parseXmlFile(file)));
        }

        return futures;
    }

    private ElectionResult parseXmlFile(File xmlFile) {
        getLogger().info("Parsing XML file: {}", xmlFile.getName());
        try {
            JAXBContext context = JAXBContext.newInstance(ElectionResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ElectionResult) unmarshaller.unmarshal(xmlFile); // Corrected method call
        } catch (Exception e) {
            getLogger().error("Error parsing XML file: {}", xmlFile.getName(), e);
            return null;
        }
    }
}
