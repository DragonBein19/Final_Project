package lt.viko.eif.nSalunov;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 * 
 * This class bootstraps the application by invoking
 * SpringApplication.run with this class as the primary source.
 */

@SpringBootApplication
public class Main{
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}