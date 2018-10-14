package it.pathfinder.rollerbot.dispatcher;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.generic.GenericResponse;
import it.pathfinder.rollerbot.config.ConfigBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class TelegramBot extends TelegramLongPollingBot
{

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private ObjectMapper objectMapper;

    private static Logger logger = LogManager.getLogger();

    private WebClient webClient;

    @PostConstruct
    public void init()
    {
        webClient = WebClient.create("http://localhost:8080");
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        Message message = update.getMessage();
        String uri = configBean.getSpringWebservicesPath() + message.getText();
        //User tgUser = message.getFrom();
        try {
            String response = webClient.get()
                                    .uri(uri)
                                    .exchange()
                                    .block()
                                    .bodyToMono(String.class)
                                    .block();
            JSONObject prettyResponse = new JSONObject(response);
            execute(new SendMessage()
                    .setText(prettyResponse.toString(1))
                    .setReplyToMessageId(message.getMessageId())
                    .setChatId(message.getChatId()));
        } catch (JSONException | TelegramApiException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return configBean.getTelegramBotUsername();
    }

    @Override
    public String getBotToken() {
        return configBean.getTelegramBotToken();
    }
}
