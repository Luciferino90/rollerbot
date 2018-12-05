package it.pathfinder.rollerbot.service;

import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.Default;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.service.CustomService;
import it.pathfinder.rollerbot.data.service.DefaultService;
import it.pathfinder.rollerbot.data.service.StatsService;
import it.pathfinder.rollerbot.utility.PrivateNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VariablesService {

    @Autowired
    private CustomService customService;

    @Autowired
    private DefaultService defaultService;

    @Autowired
    private StatsService statsService;

    @Autowired
    private PrivateNames privateNames;

    public String manageStrings(TelegramUser telegramUser, String expression) {
        return manage(telegramUser, expression);
    }

    // while nothing is found:
    //      First we check for default
    //          then we check for custom

    public String manage(TelegramUser telegramUser, String expression) {
        // Retrieve list of default and list of var
        List<String> defaultList = privateNames.getPrivateCommandMap()
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        List<String> statsList = privateNames.getPrivateVariableMap()
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        List<String> customList = customService.findByPathfinderPg(telegramUser.getDefaultPathfinderPg())
                .map(Custom::getCustomName)
                .collect(Collectors.toList());

        List<String> vars = Stream.concat(
                defaultList.stream(),
                Stream.concat(
                        statsList.stream(),
                        customList.stream()
                )
            ).sorted((a, b) -> Integer.compare(b.length(), a.length()))
                .collect(Collectors.toList());

        int count = 0;
        boolean found = true;
        while (found) {
            String key = vars.get(count);
            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(expression);
            count++;
            while (matcher.find()) {
                //substitution
                if (defaultList.contains(key))
                    expression = replacingDefault(key, expression);
                else if (statsList.contains(key))
                    expression = replacingStats(key, expression, telegramUser.getDefaultPathfinderPg());
                else if (customList.contains(key))
                    expression = replacingCustom(expression, key, telegramUser);
                count = 0;
            }
            if (count == vars.size())
                found = false;
        }
        return expression;
    }

    private String replacingDefault(String key, String expression) {
        Mono<Default> defaultObj = defaultService.get(key);
        return defaultObj.map(aDefault -> expression.replace(key, aDefault.getCommand())).defaultIfEmpty(expression).block();
    }

    private String replacingStats(String key, String expression, PathfinderPg pathfinderPg) {
        return expression.replace(key, String.valueOf(statsService.get(pathfinderPg, key)));
    }

    private String replacingCustom(String expression, String key, TelegramUser telegramUser) {
        Mono<Custom> custom = customService.findByUserAndCustomNameAndPathfinderPg(telegramUser, key, telegramUser.getDefaultPathfinderPg());
        return custom.map(custom1 -> expression.replace(key, custom1.getCustomValue())).defaultIfEmpty(expression).block();
    }

}
