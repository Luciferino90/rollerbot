package it.pathfinder.rollerbot.service;

import it.pathfinder.rollerbot.data.entity.CustomThrows;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.service.CustomThrowsService;
import it.pathfinder.rollerbot.utility.PrivateNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VariablesService {

    @Autowired
    private CustomThrowsService customThrowsService;

    @Autowired
    private PrivateNames privateNames;

    public String manageStrings(TelegramUser telegramUser, String expression)
    {
        return manageCustomCommands(telegramUser, expression);
    }

    private String defaultThrows(TelegramUser telegramUser, String expression) {
        List<String> variableList = privateNames.getPrivateCommandMap()
                .keySet()
                .stream()
                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
                .collect(Collectors.toList());

        for (String variable : variableList) {
            if (expression.contains(variable)) {
                expression = expression; //defaultThrows(telegramUser, expression.replace(variable, fetchVariable()));
            }
        }
        return expression;
    }

    private String manageCustomCommands(TelegramUser telegramUser, String expression)
    {
        Optional<CustomThrows> customThrows = customThrowsService.findByCustomNameAndUser(telegramUser, expression);
        if (customThrows.isPresent())
            return customThrows.get().getCustomCommand();
        else
            return expression;
    }

    public Optional<CustomThrows> findByCustomNameAndTelegramUser(String variable, TelegramUser telegramUser)
    {
        return Optional.empty();
    }



}
