package it.pathfinder.rollerbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "it.pathfinder.rollerbot")
@EnableJpaRepositories
public class RollerBot {

    public static void main(String[] args)
    {
        SpringApplication.run(RollerBot.class, args);
    }

}
