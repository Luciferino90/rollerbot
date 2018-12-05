package it.pathfinder.rollerbot.webflux.handler;

import dto.request.custom.Request;
import it.pathfinder.rollerbot.webflux.controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

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

    protected Request readRequest(ServerRequest serverRequest) {
        return Request.builder()
                .tgOid(Long.parseLong(serverRequest.queryParam("tgOid").orElse("")))
                .name(serverRequest.pathVariables().containsKey("name") ? serverRequest.pathVariable("name") : null)
                .value(serverRequest.pathVariables().containsKey("value") ? serverRequest.pathVariable("value") : null)
                .build();
    }


}
