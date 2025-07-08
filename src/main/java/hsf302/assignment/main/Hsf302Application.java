package hsf302.assignment.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "hsf302.assignment")
@EntityScan(basePackages = "hsf302.assignment.pojo")
@EnableJpaRepositories(basePackages = "hsf302.assignment.repository")
public class Hsf302Application {
    public static void main(String[] args) {
        SpringApplication.run(Hsf302Application.class, args);
    }
}
