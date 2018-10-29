package it.pathfinder.rollerbot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBotFallback extends TelegramLongPollingBot {
    private static Logger logger = LoggerFactory.getLogger(TelegramLongPollingBot.class);
    WebClient client = WebClient.create();
    private String token;
    private TelegramBot telegramBot;

    public TelegramBotFallback(String token, TelegramBot telegramBot) {
        this.token = token;
        this.telegramBot = telegramBot;
    }

    @Override
    public void onUpdateReceived(Update update) {
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