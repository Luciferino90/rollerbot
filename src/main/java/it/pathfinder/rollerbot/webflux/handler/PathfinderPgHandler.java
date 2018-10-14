package it.pathfinder.rollerbot.webflux.handler;

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
import org.telegram.telegrambots.meta.api.objects.User;
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
        User tgUser = objectMapper.convertValue(request.queryParam("user"), User.class);
        String characterName = request.queryParam("characterName").orElse("");

        TelegramUser telegramUser = telegramUserService.findOrRegister(tgUser);
        logger.info("@{}: {}", tgUser.getUserName(), characterName);
        PathfinderPg pathfinderPg = pathfinderPgService.create(characterName, telegramUser);
        return response(new PathfinderPgDetail(pathfinderPg));
    }

    public Mono<ServerResponse> getCharacter(ServerRequest request)
    {
        Long oid = Long.parseLong(request.pathVariable("oid"));
        PathfinderPg pathfinderPg = pathfinderPgService.findByOid(oid);
        return response(new PathfinderPgDetail(pathfinderPg));
    }

}
