package it.pathfinder.rollerbot.webflux.handler;

import dto.response.generic.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class DefaultHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(DefaultHandler.class);

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest) {
        return defaultController.set(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest) {
        return defaultController.reset(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return defaultController.delete(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return defaultController.get(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(defaultController.list(readRequest(serverRequest))
                .map(GenericResponse::new), GenericResponse.class);
    }

}
