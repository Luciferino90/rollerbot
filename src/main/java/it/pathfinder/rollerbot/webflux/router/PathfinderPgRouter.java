package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.webflux.handler.PathfinderPgHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PathfinderPgRouter extends BasicRouter {

    @Bean
    public RouterFunction<ServerResponse> routeGet(PathfinderPgHandler pathfinderPgHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/pg/findByCharacter/{name}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::get)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/pg/set/{name}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::set)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/pg/delete/{name}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::delete)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/pg/list")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::list)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/pg/default")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::defaultCharacter)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/pg/use/{name}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::useCharacter);
    }

}