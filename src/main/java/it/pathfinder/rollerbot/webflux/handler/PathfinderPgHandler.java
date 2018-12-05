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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PathfinderPgHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(BasicHandler.class);

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest) {
        return pathfinderPgController.set(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest) {
        return pathfinderPgController.reset(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return pathfinderPgController.get(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return pathfinderPgController.delete(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(pathfinderPgController.list(readRequest(serverRequest))
                .map(GenericResponse::new), GenericResponse.class);
    }

    public Mono<ServerResponse> defaultCharacter(ServerRequest serverRequest) {
        return pathfinderPgController.defaultCharacter(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

    public Mono<ServerResponse> useCharacter(ServerRequest serverRequest) {
        return pathfinderPgController.useCharacter(readRequest(serverRequest))
                .map(GenericResponse::new)
                .flatMap(genericResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(genericResponse)));
    }

}
