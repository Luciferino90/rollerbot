package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.service.VariablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Controller
public class GenericController extends BasicController {

    private Logger logger = LoggerFactory.getLogger(GenericController.class);

    @Autowired
    private VariablesService variablesService;

    public Mono<GenericDTO> diceRoller(ServerRequest request) {
        return telegramUserService.findByTgOid(Long.parseLong(request.queryParam("tgOid").orElse("0")))
                        .doOnError( e -> telegramUserService.createAnonUser())
                .map(telegramUser -> Tuples.of(telegramUser, variablesService.manageStrings(telegramUser, request.pathVariable("expression"))))
                .map(tuple2 -> {
                    logger.info("@{}: {}", tuple2.getT1().getTgUsername(), tuple2.getT2());
                    return parserService.parseFormula(tuple2.getT2(), tuple2.getT1().getTgUsername());
                });
    }

}
