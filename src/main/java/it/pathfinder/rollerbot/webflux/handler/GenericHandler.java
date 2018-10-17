package it.pathfinder.rollerbot.webflux.handler;

import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GenericHandler extends BasicHandler {

    private Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    public Mono<ServerResponse> diceRoller(ServerRequest request)
    {

        TelegramUser tgUser = telegramUserService.findByTgOid(Long.parseLong(request.queryParam("tgOid").orElse("0")));
        String expression = request.pathVariable("expression");
        logger.info("@{}: {}", tgUser.getTgUsername(), expression);
        return response(parserService.parseFormula(expression, tgUser.getTgUsername()));
    }

    public Mono<ServerResponse> helloWorld(ServerRequest request)
    {
        return response("Hello, World!");
    }

}
