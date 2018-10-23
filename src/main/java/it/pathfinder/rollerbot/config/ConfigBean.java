package it.pathfinder.rollerbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ConfigBean {

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.username}")
    private String telegramBotUsername;

    @Value("${spring.webservices.path}")
    private String springWebservicesPath;

    @Value("${server.port}")
    private Integer serverPort;

}
