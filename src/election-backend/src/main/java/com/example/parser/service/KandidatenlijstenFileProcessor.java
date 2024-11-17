//package com.example.parser.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class KandidatenlijstenFileProcessor extends FileProcessingService {
//
//    private static final Logger logger = LoggerFactory.getLogger(KandidatenlijstenFileProcessor.class);
//    private static final String DIRECTORY_PATH = "src/main/resources/ElectionResults/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)/Kandidatenlijsten";
//    private static final String FILE_PREFIX = "Kandidatenlijsten_TK2023_";
//
//    @Override
//    protected List<File> getFiles() {
//        List<File> files = new ArrayList<>();
//        File directory = new File(DIRECTORY_PATH);
//
//        if (directory.exists() && directory.isDirectory()) {
//            File[] matchedFiles = directory.listFiles((dir, name) -> name.startsWith(FILE_PREFIX) && name.endsWith(".xml"));
//            if (matchedFiles != null) {
//                for (File file : matchedFiles) {
//                    files.add(file);
//                }
//            }
//        } else {
//            logger.warn("Directory niet gevonden: {}", DIRECTORY_PATH);
//        }
//
//        return files;
//    }
//
//    @Override
//    protected Logger getLogger() {
//        return logger;
//    }
//}