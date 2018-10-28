package it.pathfinder.rollerbot.webflux.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PathfinderPgHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(BasicHandler.class);

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest) {
        return response(pathfinderPgController.set(serverRequest));
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest) {
        return response(pathfinderPgController.reset(serverRequest));
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return response(pathfinderPgController.get(serverRequest));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return response(pathfinderPgController.delete(serverRequest));
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return response(pathfinderPgController.list(serverRequest));
    }

    public Mono<ServerResponse> defaultCharacter(ServerRequest serverRequest) {
        return response(pathfinderPgController.defaultCharacter(serverRequest));
    }

    public Mono<ServerResponse> useCharacter(ServerRequest serverRequest) {
        return response(pathfinderPgController.useCharacter(serverRequest));
    }

}
