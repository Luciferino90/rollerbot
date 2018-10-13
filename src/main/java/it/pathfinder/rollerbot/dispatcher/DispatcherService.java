package it.pathfinder.rollerbot.dispatcher;

import it.pathfinder.rollerbot.dao.entity.PathfinderPg;
import it.pathfinder.rollerbot.dao.entity.TelegramUser;
import it.pathfinder.rollerbot.dao.service.PathfinderPgService;
import it.pathfinder.rollerbot.dao.service.TelegramUserService;
import it.pathfinder.rollerbot.response.MultiResponse;
import it.pathfinder.rollerbot.response.SingleDiceResponse;
import it.pathfinder.rollerbot.service.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.User;

@RestController
public class DispatcherService {

    // Add AOP controller
    private Logger logger = LoggerFactory.getLogger(DispatcherService.class);

    @Autowired
    private ParserService parserService;

    @Autowired
    private PathfinderPgService pathfinderPgService;

    @Autowired
    private TelegramUserService telegramUserService;

    @GetMapping(value = "/api/{expression}")
    public MultiResponse diceRoller(@PathVariable("expression") String expression, User tgUser)
    {
        logger.info(String.format("@%s: %s", tgUser.getUserName(), expression));
        MultiResponse multiResponse = parserService.parseFormula(expression, tgUser.getUserName());
        return multiResponse;
    }

    @GetMapping(value = "/api/create/{name}")
    public MultiResponse createCharacter(@PathVariable("name") String characterName, User tgUser)
    {
        TelegramUser telegramUser = telegramUserService.findOrRegister(tgUser);
        logger.info(String.format("@%s: %s", tgUser.getUserName(), characterName));
        PathfinderPg pathfinderPg = pathfinderPgService.create(characterName, telegramUser);
        SingleDiceResponse singleDiceResponse = new SingleDiceResponse(tgUser.getUserName());
        singleDiceResponse.setResult(String.format("Personaggio registrato: %s", pathfinderPg.toString()));
        MultiResponse multiResponse = new MultiResponse(tgUser.getUserName());
        multiResponse.addSingleDiceResponse(singleDiceResponse);
        return multiResponse;
    }

    @GetMapping(value = "/api/get/{oid}")
    public MultiResponse getCharacter(@PathVariable("oid") Long oid, User tgUser)
    {
        SingleDiceResponse singleDiceResponse = new SingleDiceResponse(tgUser.getUserName());
        singleDiceResponse.setResult(pathfinderPgService.findByOid(oid).toString());
        MultiResponse multiResponse = new MultiResponse(tgUser.getUserName());
        multiResponse.addSingleDiceResponse(singleDiceResponse);
        return multiResponse;
    }

}
