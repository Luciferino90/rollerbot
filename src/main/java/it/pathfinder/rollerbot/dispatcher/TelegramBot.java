package it.pathfinder.rollerbot.dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.PostConstruct;

@Component
public class TelegramBot extends TelegramLongPollingBot
{

    @Value("${spring.webservices.path}")
    private String springWebservicesPath;

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.username}")
    private String telegramBotUsername;

    private static Logger logger = LogManager.getLogger();

    private WebClient webClient;

    @PostConstruct
    public void init()
    {
        webClient = WebClient.create("localhost:8080");
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        User tgUser = update.getMessage().getFrom();
        Integer replyTo = update.getMessage().getMessageId();
        try {
            execute(new SendMessage()
                    .setText(webClient.get()
                            .uri(springWebservicesPath+"/"+update
                                    .getMessage()
                                    .getText())
                            .exchange()
                            .toString())
                    .setReplyToMessageId(replyTo));
        } catch (TelegramApiException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotUsername;
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }
}
