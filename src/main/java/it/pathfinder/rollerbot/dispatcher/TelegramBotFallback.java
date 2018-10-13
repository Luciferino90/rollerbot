package it.pathfinder.rollerbot.dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBotFallback extends TelegramLongPollingBot
{
    private String token;
    private TelegramBot telegramBot;

    WebClient client = WebClient.create();

    private static Logger logger = LogManager.getLogger();

    public TelegramBotFallback(String token, TelegramBot telegramBot){
        this.token = token;
        this.telegramBot = telegramBot;
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        logger.info("working");
        telegramBot.onUpdateReceived(update);
    }

    @Override
    public String getBotUsername() {
        return "RollerBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

}