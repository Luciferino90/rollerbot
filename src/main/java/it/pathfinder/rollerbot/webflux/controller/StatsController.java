package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import dto.generic.entity.StatsDetail;
import dto.request.custom.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class StatsController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Override
    public Mono<GenericDTO> set(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMap(telegramUser -> statsService.set(telegramUser.getDefaultPathfinderPg(), request.getName(), Integer.valueOf(request.getValue())))
                .map(StatsDetail::new);
    }

    @Override
    public Mono<GenericDTO> get(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMap(telegramUser -> statsService.findByCharacter(telegramUser.getDefaultPathfinderPg()))
                .map(StatsDetail::new);
    }
    
    @Override
    public Flux<GenericDTO> list(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMapMany(telegramUser -> statsService.list(telegramUser))
                .map(StatsDetail::new);
    }

    @Override
    public Mono<GenericDTO> reset(Request request) {
        return Mono.empty();
    }

    @Override
    public Mono<GenericDTO> delete(Request request) {
        return Mono.empty();
    }

}
