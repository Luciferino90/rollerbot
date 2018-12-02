package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import dto.generic.entity.CustomDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.exception.CustomException;
import it.pathfinder.rollerbot.exception.TelegramUserException;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Controller
public class CustomController extends BasicController implements DaoController {

    @Override
    public Mono<GenericDTO> get(Request request) {
        return Mono.just(request)
                    .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(()-> new TelegramUserException("User not found: " + req.getTgOid())))
                    .map(user -> customService.findByUserAndCustomNameAndPathfinderPg(user, request.getName(), user.getDefaultPathfinderPg()).orElseThrow(()-> new TelegramUserException("Custom not found for " + request.getName() + " and " + user.getDefaultPathfinderPg().getName())))
                    .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> set(Request request) {
        return Mono.just(request)
                .map(req -> customService.setIfNotExists(req.getTgOid(), req.getName(), req.getValue()).orElseThrow(() -> new CustomException("CustomFormula already present: " + req.getName() + ". Use reset to overwrite, or delete to remove it.")))
                .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> reset(Request request) {
        return Mono.just(request)
                .map(req -> customService.overwriteIfExists(req.getTgOid(), req.getName(), req.getValue()).orElseThrow(() -> new CustomException("CustomFormula set failed: " + request.getName() + ". Please try again later.")))
                .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> delete(Request request) {
        return Mono.just(request)
                .map(req -> customService.delete(req.getTgOid(), req.getName()).orElseThrow(() -> new CustomException("CustomFormula delete failed: " + request.getName() + ". Please try again later.")))
                .map(CustomDetail::new);
    }


    @Override
    public Mono<GenericDTO> list(Request request) {
        return Mono.just(request)
                .map(req -> customService.findByUserOid(req.getTgOid()).orElseThrow(() -> new CustomException("No custom command found for user " + req.getTgOid())))
                .map(customs -> new ResponseList(customs.stream().map(CustomDetail::new).collect(Collectors.toList())));
    }

}
