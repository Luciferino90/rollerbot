package it.pathfinder.rollerbot.dispatcher;

import it.pathfinder.rollerbot.dao.entity.TelegramUser;
import it.pathfinder.rollerbot.response.MultiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;


public class TelegramBot extends TelegramLongPollingBot
{
    private String token;
    private DispatcherService dispatcherService;
    private String warningMessage = "Questo boot non è ancora interattivo, per adesso funziona da dispatcher di notifiche.";

    WebClient client = WebClient.create();

    private static Logger logger = LogManager.getLogger();

    public TelegramBot(String token, DispatcherService dispatcherService){
        this.token = token;
        this.dispatcherService = dispatcherService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        SendMessage message = new SendMessage();
        User tgUser = update.getMessage().getFrom();
        MultiResponse response = new MultiResponse(tgUser.getUserName());
        Long chatId = -1L;
        if (update.hasMessage() && update.getMessage().hasText()) {
            String[] requestFull = update.getMessage().getText().toLowerCase().split(" ");
            String request = requestFull[0];

            chatId = update.getMessage().getChatId();
            switch(requestFull[0]){
                case "/create":
                    response = dispatcherService.createCharacter(requestFull[1], tgUser);
                    break;
                case "/get":
                    response = dispatcherService.getCharacter(Long.parseLong(requestFull[1]), tgUser);
                    break;
                default:
                    response = dispatcherService.diceRoller(requestFull[1], tgUser);
                    break;
            }
        }
        try {
            if (chatId != -1 && response != null) {
                message.setChatId(chatId);
                message.setText(response.toString());
                execute(message); // Call method to send the message
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String apiHelp(){
        return "Ciao! Questo bot tira i dadi!";
    }

    public void sendNotification(Long chatId, String text){
        SendMessage message = new SendMessage().setChatId(chatId).setText(text);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "RollerBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    private String simpleResolver(String expression)
    {
        //return parserService.parseFormula(expression);
        return null;
    }

}
