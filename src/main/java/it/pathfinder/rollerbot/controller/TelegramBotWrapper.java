package it.pathfinder.rollerbot.controller;

import it.pathfinder.rollerbot.telegram.TelegramBot;
import it.pathfinder.rollerbot.telegram.TelegramBotFallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.annotation.PostConstruct;

@Component
public class TelegramBotWrapper {
    private static Logger logger = LoggerFactory.getLogger(TelegramBotWrapper.class);
    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(telegramBot);
            return;
        } catch (Exception e) {
            logger.error("Autowired hook failed {}", e.getMessage());
        }

        try {
            botsApi.registerBot(new TelegramBotFallback(telegramBot.getBotToken(), telegramBot));
        } catch (Exception e) {
            logger.error("Costruct hook failed {}", e.getMessage());
        }

    }
}
