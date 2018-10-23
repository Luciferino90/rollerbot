package it.pathfinder.rollerbot.webflux.handler;

import it.pathfinder.rollerbot.data.entity.CustomThrows;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.service.CustomThrowsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class GenericHandler extends BasicHandler {

    private Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    @Autowired
    private CustomThrowsService customThrowsService;

    public Mono<ServerResponse> diceRoller(ServerRequest request)
    {
        TelegramUser tgUser = telegramUserService.findByTgOid(Long.parseLong(request.queryParam("tgOid").orElse("0")))
                .orElse(telegramUserService.createAnonUser());
        String expression = manageCustomCommands(tgUser, request.pathVariable("expression"));
        logger.info("@{}: {}", tgUser.getTgUsername(), expression);

        return response(parserService.parseFormula(expression, tgUser.getTgUsername()));
    }

    private String manageCustomCommands(TelegramUser telegramUser, String expression)
    {
        Optional<CustomThrows> customThrows = customThrowsService.findByCustomNameAndUser(telegramUser, expression);
        if (customThrows.isPresent())
            return customThrows.get().getCustomCommand();
        else
            return expression;
    }

    public Mono<ServerResponse> helloWorld(ServerRequest request)
    {
        return response("Hello, World!");
    }

}
