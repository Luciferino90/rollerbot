package it.pathfinder.rollerbot.webflux.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pathfinder.rollerbot.dao.entity.PathfinderPg;
import it.pathfinder.rollerbot.dao.entity.TelegramUser;
import it.pathfinder.rollerbot.dao.service.PathfinderPgService;
import it.pathfinder.rollerbot.dao.service.TelegramUserService;
import it.pathfinder.rollerbot.response.Info;
import it.pathfinder.rollerbot.response.dao.PathfinderPgDetail;
import it.pathfinder.rollerbot.service.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Mono;

@Component
public class PathfinderPgHandler {

    private Logger logger = LoggerFactory.getLogger(PathfinderPgHandler.class);

    @Autowired
    private ParserService parserService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private PathfinderPgService pathfinderPgService;

    @Autowired
    private ObjectMapper objectMapper;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject("Hello, Spring!"));
    }

    public Mono<ServerResponse> diceRoller(ServerRequest request)
    {
        User tgUser = objectMapper.convertValue(request.queryParam("user"), User.class);
        String expression = request.pathVariable("expression");
        logger.info("@{}: {}", tgUser.getUserName(), expression);
        return response(Mono.just(parserService.parseFormula(expression, tgUser.getUserName())));
    }

    public Mono<ServerResponse> createCharacter(ServerRequest request)
    {
        User tgUser = objectMapper.convertValue(request.queryParam("user"), User.class);
        String characterName = request.queryParam("characterName").orElse("");

        TelegramUser telegramUser = telegramUserService.findOrRegister(tgUser);
        logger.info("@{}: {}", tgUser.getUserName(), characterName);
        PathfinderPg pathfinderPg = pathfinderPgService.create(characterName, telegramUser);
        return response(Mono.just(new PathfinderPgDetail(pathfinderPg)));
    }

    public Mono<ServerResponse> getCharacter(ServerRequest request)
    {
        Long oid = Long.parseLong(request.pathVariable("oid"));
        PathfinderPg pathfinderPg = pathfinderPgService.findByOid(oid);
        return response(Mono.just(new PathfinderPgDetail(pathfinderPg)));
    }

    public Mono<ServerResponse> response(Mono<Info> infoMono)
    {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(infoMono, Info.class));
    }


}
