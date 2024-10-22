package com.example.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectionParser {

    public static void main(String[] args) {
        System.out.println("Initializing Election Parser application...");
        SpringApplication.run(ElectionParser.class, args);
        System.out.println("Election Parser application started successfully.");
    }
}
