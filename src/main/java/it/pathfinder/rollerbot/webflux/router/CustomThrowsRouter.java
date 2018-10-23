package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.webflux.handler.CustomThrowsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CustomThrowsRouter extends BasicRouter {

    @Bean
    public RouterFunction<ServerResponse> daoRouting(CustomThrowsHandler customThrowsHandler)
    {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/set/{formulaName}/{formulaValue}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::setCustomFormula)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/reset/{formulaName}/{formulaValue}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::resetCustomFormula)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/delete/{formulaName}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::deleteCustomFormula)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/list/formula")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::listCustomFormula);

    }

}
