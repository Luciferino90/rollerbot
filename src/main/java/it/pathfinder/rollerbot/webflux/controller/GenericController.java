package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.service.VariablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;

@Controller
public class GenericController extends BasicController {

    private Logger logger = LoggerFactory.getLogger(GenericController.class);

    @Autowired
    private VariablesService variablesService;

    public GenericDTO diceRoller(ServerRequest request) {
        TelegramUser tgUser = telegramUserService.findByTgOid(Long.parseLong(request.queryParam("tgOid").orElse("0")))
                .orElse(telegramUserService.createAnonUser());

        String expression = variablesService.manageStrings(tgUser, request.pathVariable("expression"));

        // DO math
        logger.info("@{}: {}", tgUser.getTgUsername(), expression);
        return parserService.parseFormula(expression, tgUser.getTgUsername());
    }

}
