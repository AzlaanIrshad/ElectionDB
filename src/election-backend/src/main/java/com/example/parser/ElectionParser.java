//package com.example.parser;
//
//import com.example.parser.service.ElectionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootApplication
//public class ElectionParser implements CommandLineRunner {
//
//    @Autowired
//    private ElectionService electionService;
//
//    @Autowired
//    private ApplicationContext context;
//
//    // Azlaan stinky
//    private static final String BASE_PATH = "src/election-backend/src/main/resources/ElectionResults";
//
//    public static void main(String[] args) {
//        SpringApplication.run(ElectionParser.class, args);
//    }
//
//    @Override
//    public void run(String... args) {
//        System.out.println("Starting XML to JSON parsing process...");
//
//        // beschikbare jaren op
//        List<Integer> years = getAvailableYears();
//
//        if (years.isEmpty()) {
//            System.out.println("Geen beschikbare jaren gevonden. Controleer de mapstructuur.");
//        } else {
//            // Log jaren
//            System.out.printf("In '%s' gevonden jaren: %s%n", BASE_PATH, years);
//
//            for (int year : years) {
//                System.out.printf("Start parsing for year: %d%n", year);
//                electionService.parseXmlFilesToJson(year);
//            }
//        }
//
//        System.out.println("Parsing process completed.");
//
//        SpringApplication.exit(context, () -> 0);
//    }
//
//    /**
//     * Haal alle beschikbare jaren op op basis van submappen in de bronmap.
//     * @return Een lijst van beschikbare jaren als integers.
//     */
//    private List<Integer> getAvailableYears() {
//        List<Integer> years = new ArrayList<>();
//        File baseDirectory = new File(BASE_PATH);
//
//        if (baseDirectory.exists() && baseDirectory.isDirectory()) {
//            File[] yearDirectories = baseDirectory.listFiles(File::isDirectory);
//
//            if (yearDirectories != null) {
//                for (File yearDir : yearDirectories) {
//                    try {
//                        int year = Integer.parseInt(yearDir.getName());
//                        years.add(year);
//                    } catch (NumberFormatException e) {
//                        System.out.printf("Ongeldige mapnaam gevonden (geen jaar): %s%n", yearDir.getName());
//                    }
//                }
//            }
//        } else {
//            System.out.printf("De basisdirectory bestaat niet of is geen map: %s%n", BASE_PATH);
//        }
//
//        return years;
//    }
//}
