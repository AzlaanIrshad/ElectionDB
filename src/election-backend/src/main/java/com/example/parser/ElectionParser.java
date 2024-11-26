//package com.example.parser;
//
//import com.example.parser.service.ElectionService;
//import com.example.parser.service.KandidatenlijstenFileProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//
//@SpringBootApplication
//public class ElectionParser implements CommandLineRunner {
//
//    @Autowired
//    private ElectionService electionService;
//
//    @Autowired
//    private KandidatenlijstenFileProcessor kandidatenlijstenFileProcessor;
//
//    @Autowired
//    private ApplicationContext context;
//
//    public static void main(String[] args) {
//        SpringApplication.run(ElectionParser.class, args);
//    }
//
//    @Override
//    public void run(String... args) {
//        System.out.println("Starting XML to JSON parsing process...");
//
//        electionService.parseXmlFilesToJson();
//
//        System.out.println("Parsing process completed.");
//
//        SpringApplication.exit(context, () -> 0);
//    }
//}
