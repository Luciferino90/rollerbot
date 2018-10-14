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
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/get").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::getCharacter);
    }

    @Bean
    public RouterFunction<ServerResponse> routeCreate(PathfinderPgHandler pathfinderPgHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/create").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), pathfinderPgHandler::createCharacter);
    }

}