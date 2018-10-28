package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.webflux.handler.DefaultHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DefaultRouter extends BasicRouter {

    @Bean
    public RouterFunction<ServerResponse> defaultRouting(DefaultHandler defaultHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/def/findByCharacter/{name}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), defaultHandler::get)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/def/list")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), defaultHandler::list);
    }

}
