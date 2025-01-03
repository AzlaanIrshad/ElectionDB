package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectionBackendApplication {
    public static void main(String[] args) {
        // Load environment variables from the .env.local file
        Dotenv dotenv = Dotenv.configure().filename(".env.local").load();


        // Set system properties to match Spring properties
        System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

        // Run Spring Boot application
        SpringApplication.run(ElectionBackendApplication.class, args);
    }
}
