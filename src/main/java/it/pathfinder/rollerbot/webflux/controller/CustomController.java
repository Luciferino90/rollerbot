package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.Error;
import dto.generic.GenericDTO;
import dto.generic.entity.CustomDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CustomController extends BasicController implements DaoController {

    @Override
    public GenericDTO set(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<Custom> res = customService.setIfNotExists(request.getTgOid(), request.getName(), request.getValue());

        return res.<GenericDTO>map(CustomDetail::new)
                .orElseGet(() ->
                        new Error("CustomFormula already present: " + request.getName() + ". Use reset to overwrite, or delete to remove it."));
    }

    @Override
    public GenericDTO reset(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<Custom> res = customService.overwriteIfExists(request.getTgOid(), request.getName(), request.getValue());

        return res.<GenericDTO>map(CustomDetail::new)
                .orElseGet(() ->
                        new Error("CustomFormula set failed: " + request.getName() + ". Please try again later."));

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
    public GenericDTO get(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        TelegramUser telegramUser = telegramUserService.findByTgOid(request.getTgOid()).orElse(null);

        if (Objects.requireNonNull(telegramUser).getDefaultPathfinderPg() == null)
            return new Error("User not registered");
        if (!customService.findByUserAndCustomNameAndPathfinderPg(telegramUser, request.getName(), telegramUser.getDefaultPathfinderPg()).isPresent())
            return new Error("No var found for var " + request.getName() + " tgUser " + telegramUser.getTgUsername() +
                    (telegramUser.getDefaultPathfinderPg() != null ? String.format(" and character name %s", telegramUser.getDefaultPathfinderPg().getName()) : ""));

        Optional<Custom> res = customService.findByUserAndCustomNameAndPathfinderPg(telegramUser, request.getName(), telegramUser.getDefaultPathfinderPg());

        return res.<GenericDTO>map(CustomDetail::new)
                .orElseGet(() ->
                        new Error("CustomFormula not found: " + request.getName() + "."));
    }

    @Override
    public GenericDTO list(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<List<Custom>> res = customService.findByUserOid(request.getTgOid());
        return new ResponseList(res.orElse(new ArrayList<>()).stream().map(CustomDetail::new).collect(Collectors.toList()));
    }


}
