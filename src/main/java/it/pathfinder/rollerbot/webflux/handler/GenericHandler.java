package it.pathfinder.rollerbot.webflux.handler;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.CustomThrows;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.service.CustomThrowsService;
import it.pathfinder.rollerbot.service.VariablesService;
import it.pathfinder.rollerbot.utility.PrivateNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GenericHandler extends BasicHandler {

    private Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    @Autowired
    private VariablesService variablesService;

    public Mono<ServerResponse> diceRoller(ServerRequest request)
    {
        TelegramUser tgUser = telegramUserService.findByTgOid(Long.parseLong(request.queryParam("tgOid").orElse("0")))
                .orElse(telegramUserService.createAnonUser());

        // Find variables
        String expression = variablesService.manageStrings(tgUser, request.pathVariable("expression"));

        // DO math
        logger.info("@{}: {}", tgUser.getTgUsername(), expression);
        GenericDTO result = parserService.parseFormula(expression, tgUser.getTgUsername());
        return response(result);
    }

    public Mono<ServerResponse> helloWorld(ServerRequest request)
    {
        return response("Hello, World!");
    }


}
