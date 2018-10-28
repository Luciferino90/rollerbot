package it.pathfinder.rollerbot.webflux.controller;

import dto.request.custom.Request;
import it.pathfinder.rollerbot.data.service.*;
import it.pathfinder.rollerbot.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;

@Controller
public class BasicController {

    @Autowired
    ParserService parserService;

    @Autowired
    TelegramUserService telegramUserService;

    @Autowired
    PathfinderPgService pathfinderPgService;

    @Autowired
    StatsService statsService;

    @Autowired
    CustomService customService;

    @Autowired
    DefaultService defaultService;

    protected Request readRequest(ServerRequest serverRequest) {
        Long tgOid = Long.parseLong(serverRequest.queryParam("tgOid").orElse(""));
        String name = serverRequest.pathVariables().containsKey("name") ? serverRequest.pathVariable("name") : null;
        String value = serverRequest.pathVariables().containsKey("value") ? serverRequest.pathVariable("value") : null;
        return new Request(tgOid, name, value);
    }


}
