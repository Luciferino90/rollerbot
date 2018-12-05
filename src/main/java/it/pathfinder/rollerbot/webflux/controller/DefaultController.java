package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import dto.generic.entity.DefaultDetail;
import dto.request.custom.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .flatMap(req -> defaultService.get(req.getName()))
                .map(DefaultDetail::new);
    }

    @Override
    public Flux<GenericDTO> list(Request request) {
        return defaultService.findAll()
                .map(DefaultDetail::new);
    }

}
