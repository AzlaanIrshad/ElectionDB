package ElectionApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // This annotation enables auto-configuration and component scanning
public class ElectionApplication { // You can name this class anything you like

    public static void main(String[] args) {
        SpringApplication.run(ElectionApplication.class, args); // This line starts the application
    }
}