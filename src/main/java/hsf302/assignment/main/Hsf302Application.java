package hsf302.assignment.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "hsf302.assignment.pojo")
public class Hsf302Application {
    public static void main(String[] args) {
        SpringApplication.run(Hsf302Application.class, args);
    }
}

