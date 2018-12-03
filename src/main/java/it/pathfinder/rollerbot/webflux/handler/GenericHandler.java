package it.pathfinder.rollerbot.webflux.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GenericHandler extends BasicHandler {

    private Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    public Mono<ServerResponse> diceRoller(ServerRequest serverRequest) {
        return response(genericController.diceRoller(serverRequest).block());
    }

}
