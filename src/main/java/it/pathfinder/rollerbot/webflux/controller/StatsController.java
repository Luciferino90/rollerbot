package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import dto.generic.entity.StatsDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.exception.StatsException;
import it.pathfinder.rollerbot.exception.TelegramUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Controller
public class StatsController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Override
    public Mono<GenericDTO> set(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> statsService.set(telegramUser.getDefaultPathfinderPg(), request.getName(), Integer.valueOf(request.getValue())))
                .map(StatsDetail::new);
    }

    @Override
    public Mono<GenericDTO> get(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> statsService.findByCharacter(telegramUser.getDefaultPathfinderPg())
                        .orElseThrow(() -> new StatsException("No stats found for " + telegramUser.getDefaultPathfinderPg().getName())))
                .map(StatsDetail::new);
    }

    @Override
    public Mono<GenericDTO> list(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> new ResponseList(statsService.list(telegramUser).stream().map(StatsDetail::new).collect(Collectors.toList())));
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
