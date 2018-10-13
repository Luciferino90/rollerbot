package it.pathfinder.rollerbot.controller;

import it.pathfinder.rollerbot.dispatcher.TelegramBot;
import it.pathfinder.rollerbot.dispatcher.TelegramBotFallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Component
public class TelegramBotWrapper
{
    @Autowired
    private TelegramBot telegramBot;

    private static Logger logger = LogManager.getLogger();

    @PostConstruct
    public void init(){
        ApiContextInitializer.init();
        logger.info("Trying to hook to the bot!");
        if (!(registerBot(telegramBot) || registerBot(new TelegramBotFallback(telegramBot.getBotToken(), telegramBot))))
            logger.error("Hook to bot failed");
        else
            logger.info("Hooked successful");
    }

    private boolean registerBot(TelegramLongPollingBot telegramLongPollingBot)
    {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        boolean hooked = false;
        try {
            botsApi.registerBot(telegramLongPollingBot);
            hooked = true;
        } catch (TelegramApiRequestException e) {
            logger.error(e.getMessage());
        }
        return hooked;
    }

}
