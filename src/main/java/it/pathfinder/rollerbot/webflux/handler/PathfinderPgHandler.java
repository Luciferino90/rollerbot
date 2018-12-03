package it.pathfinder.rollerbot.webflux.handler;

import dto.request.custom.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PathfinderPgHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(BasicHandler.class);

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(pathfinderPgController.set(request))));
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(pathfinderPgController.reset(request))));
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(pathfinderPgController.get(request))));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(pathfinderPgController.delete(request))));
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(pathfinderPgController.list(request))));
    }

    public Mono<ServerResponse> defaultCharacter(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(pathfinderPgController.defaultCharacter(request))));
    }

    public Mono<ServerResponse> useCharacter(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(pathfinderPgController.useCharacter(request))));
    }

}
