package it.pathfinder.rollerbot.webflux.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.generic.DataDTO;
import dto.generic.GenericDTO;
import dto.response.generic.GenericResponse;
import it.pathfinder.rollerbot.data.service.PathfinderPgService;
import it.pathfinder.rollerbot.data.service.TelegramUserService;
import it.pathfinder.rollerbot.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public abstract class BasicHandler {

    @Autowired
    ParserService parserService;

    @Autowired
    TelegramUserService telegramUserService;

    @Autowired
    PathfinderPgService pathfinderPgService;

    @Autowired
    ObjectMapper objectMapper;

    Mono responseT(GenericDTO data)
    {
        return Mono.just(new GenericResponse(data));
    }

    Mono<ServerResponse> response(GenericDTO data)
    {
        GenericResponse response = new GenericResponse();
        response.setData(data);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(Mono.just(response), GenericResponse.class));
    }

    Mono<ServerResponse> response(String plainText)
    {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(plainText));
    }

}
