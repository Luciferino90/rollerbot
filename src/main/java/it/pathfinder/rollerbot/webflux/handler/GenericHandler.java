package it.pathfinder.rollerbot.webflux.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Mono;

@Component
public class GenericHandler extends BaseHandler {

    private Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    public Mono<ServerResponse> diceRoller(ServerRequest request)
    {
        User tgUser = objectMapper.convertValue(request.queryParam("user"), User.class);
        String expression = request.pathVariable("expression");
        logger.info("@{}: {}", tgUser.getUserName(), expression);
        return response(Mono.just(parserService.parseFormula(expression, tgUser.getUserName())));
    }

    public Mono<ServerResponse> helloWorld(ServerRequest request)
    {
        return response("Hello, World!");
    }

}
