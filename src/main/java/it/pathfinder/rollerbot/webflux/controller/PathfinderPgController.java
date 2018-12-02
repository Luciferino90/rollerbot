package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import dto.generic.entity.PathfinderPgDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.exception.PathfinderPgException;
import it.pathfinder.rollerbot.exception.TelegramUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.stream.Collectors;

@Controller
public class PathfinderPgController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Override
    public Mono<GenericDTO> set(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser)
                        .orElseThrow(() -> new PathfinderPgException("No character found for user " + telegramUser.getTgUsername() + " and name " + request.getName())))
                .map(PathfinderPgDetail::new);
    }

    @Override
    public Mono<GenericDTO> reset(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> pathfinderPgService.set(request.getName(), telegramUser))
                .map(PathfinderPgDetail::new);
    }

    @Override
    public Mono<GenericDTO> get(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser)
                        .orElseThrow(() -> new PathfinderPgException("No pathfinder pg found for telegram user " + telegramUser.getTgUsername() + " and name " +request.getName())))
                .map(PathfinderPgDetail::new);
    }

    @Override
    public Mono<GenericDTO> delete(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> pathfinderPgService.delete(request.getName(), telegramUser)
                        .orElseThrow(() -> new PathfinderPgException("No pathfinder pg found for telegram user " + telegramUser.getTgUsername() + " and name " +request.getName())))
                .map(PathfinderPgDetail::new);
    }

    @Override
    public Mono<GenericDTO> list(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> new ResponseList(pathfinderPgService.list(telegramUser).stream().map(PathfinderPgDetail::new).collect(Collectors.toList())));
    }

    public Mono<GenericDTO> defaultCharacter(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(TelegramUser::getDefaultPathfinderPg)
                    .switchIfEmpty(Mono.error(new PathfinderPgException("No default pathfinder pg found")))
                .map(PathfinderPgDetail::new);
    }

    public Mono<GenericDTO> useCharacter(Request request) {
        return Mono.just(request)
                .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(() -> new TelegramUserException("No telegram user found for oid " + req.getTgOid())))
                .map(telegramUser -> Tuples.of(telegramUser, pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser)
                        .orElseThrow(() -> new PathfinderPgException("No character found for user " + telegramUser.getTgUsername() + " and name " + request.getName()))))
                .map(tuple2 -> telegramUserService.setDefault(tuple2.getT1(), tuple2.getT2()))
                .map(TelegramUser::getDefaultPathfinderPg)
                .map(PathfinderPgDetail::new);
    }




}