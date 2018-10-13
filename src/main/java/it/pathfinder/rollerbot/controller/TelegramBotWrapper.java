package it.pathfinder.rollerbot.controller;

import it.pathfinder.rollerbot.dispatcher.DispatcherService;
import it.pathfinder.rollerbot.dispatcher.TelegramBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Controller
public class TelegramBotWrapper
{

    //@Value("${telegram.bot.token}")
    private String token = "443957821:AAGLcIug5rtahehWyKgxU3uVDqMTUQ6MmYM";

    @Autowired
    private DispatcherService dispatcherService;

    private TelegramBot telegramBot;

    private static Logger logger = LogManager.getLogger();

    @PostConstruct
    public void init(){
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            telegramBot = new TelegramBot(token, dispatcherService);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            logger.error("Ther is already one telegram bot server on!");
        }
    }

    public void sendBotMessage(Long chatId, String message){
        if (chatId != null) {
            telegramBot.sendNotification(chatId, message);
        }
    }

}
