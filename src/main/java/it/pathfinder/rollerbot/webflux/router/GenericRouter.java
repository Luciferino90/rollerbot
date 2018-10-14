package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.webflux.handler.GenericHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GenericRouter extends BasicRouter {

    @Bean
    public RouterFunction<ServerResponse> routeDiceRoller(GenericHandler genericHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/{expression}").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), genericHandler::diceRoller);
    }

    @Bean
    public RouterFunction<ServerResponse> routeHelloWorld(GenericHandler genericHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(configBean.getSpringWebservicesPath() + "/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), genericHandler::helloWorld);
    }

}
