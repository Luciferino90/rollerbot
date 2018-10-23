package it.pathfinder.rollerbot.webflux.handler;

import dto.generic.Error;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import dto.generic.entity.PathfinderPgDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PathfinderPgHandler extends BasicHandler {

    private Logger logger = LoggerFactory.getLogger(BasicHandler.class);

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject("Hello, Spring!"));
    }

    public Mono<ServerResponse> createCharacter(ServerRequest request)
    {
        TelegramUser tgUser = telegramUserService.findByTgOid(Long.parseLong(request.queryParam("user").orElse(""))).orElse(null);
        if (tgUser == null)
            return response(new Error("Telegram user not registered"));
        String characterName = request.pathVariable("username");
        logger.info("@{}: {}", tgUser.getTgName(), characterName);
        PathfinderPg pathfinderPg = pathfinderPgService.create(characterName, tgUser);
        return response(new PathfinderPgDetail(pathfinderPg));
    }

    public Mono<ServerResponse> getCharacter(ServerRequest request)
    {
        TelegramUser tgUser = telegramUserService.findByTgOid(Long.parseLong(request.queryParam("user").orElse(""))).orElse(null);
        if (tgUser == null)
            return response(new Error("Telegram user not registered"));
        Long oid = Long.parseLong(request.pathVariable("oid"));
        PathfinderPg pathfinderPg = pathfinderPgService.findByOid(oid);
        return response(new PathfinderPgDetail(pathfinderPg));
    }

}
