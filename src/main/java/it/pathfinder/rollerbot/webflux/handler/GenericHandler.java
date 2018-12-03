package it.pathfinder.rollerbot.webflux.handler;

import dto.generic.GenericDTO;
import dto.request.custom.Request;
import dto.response.generic.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.http.HttpRequest;

@Component
public class GenericHandler extends BasicHandler {

    private Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    public Mono<ServerResponse> diceRoller(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(genericController.diceRoller(serverRequest).map(GenericResponse::new), GenericResponse.class);
        /*return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(genericController.diceRoller(serverRequest).map(GenericResponse::new), GenericResponse.class);*/
    }

}
