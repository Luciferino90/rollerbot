package it.pathfinder.rollerbot.webflux.handler;

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

@Component
public class StatsHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(BasicHandler.class);

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest) {
        return statsController.set(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return statsController.get(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(statsController.list(readRequest(serverRequest))
                        .map(GenericResponse::new), GenericResponse.class);
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest) {
        return statsController.reset(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return statsController.delete(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }
}
