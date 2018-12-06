package it.pathfinder.rollerbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.telegram.telegrambots.ApiContextInitializer;
import reactor.core.publisher.Hooks;

@EnableWebFlux
@SpringBootApplication
@EnableJpaRepositories
public class RollerBot {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        ApiContextInitializer.init();
        SpringApplication.run(RollerBot.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
