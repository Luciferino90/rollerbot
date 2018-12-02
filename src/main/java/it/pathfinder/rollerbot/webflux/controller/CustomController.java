package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.Error;
import dto.generic.GenericDTO;
import dto.generic.entity.CustomDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.exception.CustomNotFoundException;
import it.pathfinder.rollerbot.exception.TgUserNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CustomController extends BasicController implements DaoController {

    @Override
    public Mono<GenericDTO> get(Request request) {
        return Mono.just(request)
                    .map(req -> telegramUserService.findByTgOid(req.getTgOid()).orElseThrow(()-> new TgUserNotFoundException("User not found: " + req.getTgOid())))
                    .map(user -> customService.findByUserAndCustomNameAndPathfinderPg(user, request.getName(), user.getDefaultPathfinderPg()).orElseThrow(()-> new TgUserNotFoundException("Custom not found for " + request.getName() + " and " + user.getDefaultPathfinderPg().getName())))
                    .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> set(Request request) {
        return Mono.just(request)
                .map(req -> customService.setIfNotExists(req.getTgOid(), req.getName(), req.getValue()).orElseThrow(() -> new CustomNotFoundException("CustomFormula already present: " + req.getName() + ". Use reset to overwrite, or delete to remove it.")))
                .map(CustomDetail::new);
    }

    @Override
    public Mono<GenericDTO> reset(Request request) {
        return Mono.just(request)
                .map(req -> customService.overwriteIfExists(req.getTgOid(), req.getName(), req.getValue()).orElseThrow(() -> new CustomNotFoundException("CustomFormula set failed: " + request.getName() + ". Please try again later.")))
                .map(CustomDetail::new);
    }

    @Override
    public GenericDTO delete(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<Custom> res = customService.delete(request.getTgOid(), request.getName());

        return res.<GenericDTO>map(CustomDetail::new)
                .orElseGet(() ->
                        new Error("CustomFormula delete failed: " + request.getName() + ". Please try again later."));
    }

    @Override
    public GenericDTO list(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<List<Custom>> res = customService.findByUserOid(request.getTgOid());
        return new ResponseList(res.orElse(new ArrayList<>()).stream().map(CustomDetail::new).collect(Collectors.toList()));
    }


}
