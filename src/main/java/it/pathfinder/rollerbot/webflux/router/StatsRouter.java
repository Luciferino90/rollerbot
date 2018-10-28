package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.webflux.handler.StatsHandler;
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
    public RouterFunction<ServerResponse> statsRouting(StatsHandler statsHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/stat/set/{name}/{value}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), statsHandler::set)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/stat/findByCharacter")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), statsHandler::get)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/stat/delete/{name}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), statsHandler::delete)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/stat/list")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), statsHandler::list);
    }

}
