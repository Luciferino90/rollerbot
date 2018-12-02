package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.Error;
import dto.generic.GenericDTO;
import dto.generic.entity.DefaultDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.data.entity.Default;
import it.pathfinder.rollerbot.exception.DefaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class DefaultController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Override
    public Mono<GenericDTO> set(Request request) {
        return null;
    }

    @Override
    public Mono<GenericDTO> reset(Request request) {
        return null;
    }

    @Override
    public Mono<GenericDTO> delete(Request request) {
        return null;
    }

    @Override
    public Mono<GenericDTO> get(Request request) {
        return Mono.just(request)
                .map(req -> defaultService.get(req.getName()).orElseThrow(() -> new DefaultException("Default formula: " + request.getName() + " not found.")))
                .map(DefaultDetail::new);
    }

    @Override
    public Mono<GenericDTO> list(Request request) {
        return Mono.just(request)
                .map(req -> defaultService.findAll().orElseThrow(() -> new DefaultException("No default found")))
                .map(defaults -> new ResponseList(defaults.stream().map(DefaultDetail::new).collect(Collectors.toList())));
    }

}
