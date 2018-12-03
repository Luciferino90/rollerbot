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
public class CustomHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(CustomHandler.class);

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(customController.get(request))));
    }

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(customController.set(request))));
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(customController.reset(request))));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(customController.delete(request))));
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Request.class)
                .flatMap(request ->
                        ServerResponse
                                .ok()
                                .body(BodyInserters.fromObject(customController.list(request))));
    }
}
