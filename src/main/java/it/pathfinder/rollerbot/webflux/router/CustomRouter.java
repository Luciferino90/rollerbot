package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.webflux.handler.CustomHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomRouter extends BasicRouter {

    @Bean
    public RouterFunction<ServerResponse> customRouting(CustomHandler customHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/var/get/{name}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customHandler::get)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/var/set/{name}/{value}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customHandler::set)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/var/reset/{name}/{value}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customHandler::reset)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/var/delete/{name}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customHandler::delete)
                .andRoute(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/var/list")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), customHandler::list);
    }

}
