package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.Error;
import dto.generic.GenericDTO;
import dto.generic.entity.PathfinderPgDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PathfinderPgController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Override
    public GenericDTO set(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        if (telegramUser.isPresent()) {
            PathfinderPg pathfinderPg = pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser.get());
            if (pathfinderPg != null)
                return new Error("Character already created");
            return new PathfinderPgDetail(pathfinderPgService.set(request.getName(), telegramUser.get()));
        } else {
            return new Error("Telegram User not registered");
        }
    }

    @Override
    public GenericDTO reset(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        if (telegramUser.isPresent()) {
            return new PathfinderPgDetail(pathfinderPgService.set(request.getName(), telegramUser.get()));
        } else {
            return new Error("Telegram User not registered");
        }
    }

    @Override
    public GenericDTO get(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        return telegramUser.<GenericDTO>map(telegramUser1 ->
                new PathfinderPgDetail(pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser1)))
                .orElseGet(() ->
                        new Error("Telegram user not registered"));
    }

    @Override
    public GenericDTO delete(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        return telegramUser.<GenericDTO>map(telegramUser1 ->
                new PathfinderPgDetail(pathfinderPgService.delete(request.getName(), telegramUser1)))
                .orElseGet(() ->
                        new Error("Telegram user not registered"));
    }

    @Override
    public GenericDTO list(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        return telegramUser.<GenericDTO>map(telegramUser1 ->
                new ResponseList(pathfinderPgService.list(telegramUser1).stream().map(PathfinderPgDetail::new).collect(Collectors.toList())))
                .orElseGet(() ->
                        new Error("Telegram user not registered"));
    }

    public GenericDTO defaultCharacter(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());

        if (telegramUser.isPresent()) {
            PathfinderPg pathfinderPg = telegramUser.get().getDefaultPathfinderPg();
            if (pathfinderPg == null)
                return new Error("No default character set");
            return new PathfinderPgDetail(telegramUser.get().getDefaultPathfinderPg());
        } else
            return new Error("Telegram User not registered");
    }

    public GenericDTO useCharacter(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());
        if (telegramUser.isPresent()) {
            PathfinderPg pathfinderPg = pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser.get());
            telegramUserService.setDefault(telegramUser.get(), pathfinderPg);
            return new PathfinderPgDetail(telegramUser.get().getDefaultPathfinderPg());
        } else
            return new Error("Telegram user not registered");
    }

}