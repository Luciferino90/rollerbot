package it.pathfinder.rollerbot.webflux.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pathfinder.rollerbot.data.service.PathfinderPgService;
import it.pathfinder.rollerbot.data.service.TelegramUserService;
import it.pathfinder.rollerbot.response.Info;
import it.pathfinder.rollerbot.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public abstract class BaseHandler {

    @Autowired
    ParserService parserService;

    @Autowired
    TelegramUserService telegramUserService;

    @Autowired
    PathfinderPgService pathfinderPgService;

    @Autowired
    ObjectMapper objectMapper;

    Mono<ServerResponse> response(Mono<Info> infoMono)
    {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(infoMono, Info.class));
    }

    Mono<ServerResponse> response(String plainText)
    {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(plainText));
    }

}
