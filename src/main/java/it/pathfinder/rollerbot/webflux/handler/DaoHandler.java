package it.pathfinder.rollerbot.webflux.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface DaoHandler {

    Mono<ServerResponse> set(ServerRequest serverRequest);

    Mono<ServerResponse> reset(ServerRequest serverRequest);

    Mono<ServerResponse> delete(ServerRequest serverRequest);

    Mono<ServerResponse> get(ServerRequest serverRequest);

    Mono<ServerResponse> list(ServerRequest serverRequest);

}
