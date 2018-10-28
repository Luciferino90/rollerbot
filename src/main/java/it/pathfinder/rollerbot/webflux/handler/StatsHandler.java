package it.pathfinder.rollerbot.webflux.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StatsHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(BasicHandler.class);

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest) {
        return response(statsController.set(serverRequest));
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return response(statsController.get(serverRequest));
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return response(statsController.list(serverRequest));
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest) {
        return response(statsController.reset(serverRequest));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return response(statsController.delete(serverRequest));
    }
}
