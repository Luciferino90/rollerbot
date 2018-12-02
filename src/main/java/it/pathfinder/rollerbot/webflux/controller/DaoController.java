package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import dto.request.custom.Request;
import reactor.core.publisher.Mono;

public interface DaoController {

    Mono<GenericDTO> set(Request request);

    Mono<GenericDTO> reset(Request request);

    Mono<GenericDTO> delete(Request request);

    Mono<GenericDTO> get(Request request);

    Mono<GenericDTO> list(Request request);

}
