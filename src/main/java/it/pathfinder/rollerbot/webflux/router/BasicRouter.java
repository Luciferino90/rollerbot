package it.pathfinder.rollerbot.webflux.router;

import it.pathfinder.rollerbot.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class BasicRouter {

    @Autowired
    ConfigBean configBean;

}
