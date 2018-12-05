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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.stream.Collectors;

@Controller
public class PathfinderPgController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(BasicController.class);

    /**
     *         Request request = readRequest(serverRequest);
     *         Optional<TelegramUser> telegramUser = telegramUserService.findByTgOid(request.getTgOid());
     *
     *         if (telegramUser.isPresent()) {
     *             PathfinderPg pathfinderPg = pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser.get());
     *             if (pathfinderPg != null)
     *                 return new Error("Character already created");
     *             return new PathfinderPgDetail(pathfinderPgService.set(request.getName(), telegramUser.get()));
     *         } else {
     *             return new Error("Telegram User not registered");
     *         }
     *     }
     * @param request
     * @return
     */
    @Override
    public Mono<GenericDTO> set(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMap(telegramUser ->
                        (pathfinderPgService.findByNameAndTelegramUserOrEmpty(request.getName(), telegramUser)
                            .switchIfEmpty(pathfinderPgService.set(request.getName(), telegramUser)))
                        .map(PathfinderPgDetail::new));
    }

    @Override
    public Mono<GenericDTO> reset(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMap(telegramUser -> pathfinderPgService.set(request.getName(), telegramUser))
                .map(PathfinderPgDetail::new);
    }

    @Override
    public Mono<GenericDTO> get(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMap(telegramUser -> pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser))
                .map(PathfinderPgDetail::new);
    }

    @Override
    public Mono<GenericDTO> delete(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMap(telegramUser -> pathfinderPgService.delete(request.getName(), telegramUser))
                .map(PathfinderPgDetail::new);
    }

    @Override
    public Flux<GenericDTO> list(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .flatMapMany(telegramUser -> pathfinderPgService.list(telegramUser))
                .map(PathfinderPgDetail::new);
    }

    public Mono<GenericDTO> defaultCharacter(Request request) {
        return telegramUserService.findByTgOid(request.getTgOid())
                .map(TelegramUser::getDefaultPathfinderPg)
                    .switchIfEmpty(Mono.error(new PathfinderPgException("No default pathfinder pg found")))
                    .map(PathfinderPgDetail::new);
    }

    public Mono<GenericDTO> useCharacter(Request request) {
        return  telegramUserService.findByTgOid(request.getTgOid())
                .flatMap(telegramUser -> pathfinderPgService.findByNameAndTelegramUser(request.getName(), telegramUser))
                .flatMap(pathfinderPg -> telegramUserService.findByTgOid(request.getTgOid()).map(telegramUser -> {
                    telegramUser.setDefaultPathfinderPg(pathfinderPg);
                    return pathfinderPg;
                }))
                .map(PathfinderPgDetail::new);
    }




}