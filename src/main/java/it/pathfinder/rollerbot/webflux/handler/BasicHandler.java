package it.pathfinder.rollerbot.webflux.handler;

import dto.generic.GenericDTO;
import dto.response.generic.GenericResponse;
import it.pathfinder.rollerbot.webflux.controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public abstract class BasicHandler {

    @Autowired
    CustomController customController;

    @Autowired
    PathfinderPgController pathfinderPgController;

    @Autowired
    StatsController statsController;

    @Autowired
    DefaultController defaultController;

    @Autowired
    GenericController genericController;

    Mono responseT(GenericDTO data) {
        return Mono.just(new GenericResponse(data));
    }

    Mono<ServerResponse> response(GenericDTO data) {
        GenericResponse response = new GenericResponse();
        response.setData(data);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(Mono.just(response), GenericResponse.class));
    }

    Mono<ServerResponse> response(String plainText) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(plainText));
    }

}
