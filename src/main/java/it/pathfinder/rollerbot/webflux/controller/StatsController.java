package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.Error;
import dto.generic.GenericDTO;
import dto.generic.entity.StatsDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class StatsController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Override
    public GenericDTO set(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        return telegramUser.<GenericDTO>map(telegramUser1 ->
                new StatsDetail(statsService.set(telegramUser1.getDefaultPathfinderPg(), request.getName(), Integer.valueOf(request.getValue()))))
                .orElseGet(() ->
                        new Error("User not found"));
    }

    @Override
    public GenericDTO get(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        return telegramUser.<GenericDTO>map(telegramUser1 ->
                new StatsDetail(statsService.get(telegramUser1.getDefaultPathfinderPg())))
                .orElseGet(() ->
                        new Error("User not found"));
    }

    @Override
    public GenericDTO list(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        return telegramUser.<GenericDTO>map(telegramUser1 ->
                new ResponseList(statsService.list(telegramUser1).stream().map(StatsDetail::new).collect(Collectors.toList())))
                .orElseGet(() ->
                        new Error("User not found"));
    }

    @Override
    public GenericDTO reset(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public GenericDTO delete(ServerRequest serverRequest) {
        return null;
    }

}
