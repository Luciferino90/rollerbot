package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import dto.generic.entity.CustomDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.exception.CustomException;
import it.pathfinder.rollerbot.exception.TelegramUserException;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Controller
public class CustomController extends BasicController implements DaoController {

    @Override
    public Mono<GenericDTO> get(Request request) {
        return Mono.just(request)
                    .flatMap(req -> telegramUserService.findByTgOid(req.getTgOid()))
                    .flatMap(user -> customService.findByUserAndCustomNameAndPathfinderPg(user, request.getName(), user.getDefaultPathfinderPg()))
                    .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> set(Request request) {
        return Mono.just(request)
                .flatMap(req -> customService.setIfNotExists(req.getTgOid(), req.getName(), req.getValue()))
                .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> reset(Request request) {
        return Mono.just(request)
                .flatMap(req -> customService.overwriteIfExists(req.getTgOid(), req.getName(), req.getValue()))
                .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> delete(Request request) {
        return Mono.just(request)
                .flatMap(req -> customService.delete(req.getTgOid(), req.getName()))
                .map(CustomDetail::new);
    }


    @Override
    public Flux<GenericDTO> list(Request request) {
        return customService.findByUserOid(request.getTgOid())
                .map(CustomDetail::new);
    }

}
