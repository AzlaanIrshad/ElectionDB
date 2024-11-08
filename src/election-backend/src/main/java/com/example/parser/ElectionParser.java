package com.example.parser;

import com.example.parser.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ElectionParser implements CommandLineRunner {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ElectionParser.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting XML to JSON parsing process...");
        electionService.parseXmlToJson();
        System.out.println("Parsing process completed.");

        SpringApplication.exit(context, () -> 0);
    }
}
