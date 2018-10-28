package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import org.springframework.web.reactive.function.server.ServerRequest;

public interface DaoController {

    GenericDTO set(ServerRequest serverRequest);

    GenericDTO reset(ServerRequest serverRequest);

    GenericDTO delete(ServerRequest serverRequest);

    GenericDTO get(ServerRequest serverRequest);

    GenericDTO list(ServerRequest serverRequest);

}
