package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.webflux.handler.CustomThrowsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StatsRouter extends BasicRouter {

    @Bean
    public RouterFunction<ServerResponse> customThrowsRouting(CustomThrowsHandler customThrowsHandler)
    {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/throwsset/{formulaName}/{formulaValue}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::set)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/throwsreset/{formulaName}/{formulaValue}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::reset)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/throwsdelete/{formulaName}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::delete)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/throwslist/formula")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customThrowsHandler::list);

    }

}
