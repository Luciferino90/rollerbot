package it.pathfinder.rollerbot.webflux.controller;

import dto.generic.Error;
import dto.generic.GenericDTO;
import dto.generic.entity.DefaultDetail;
import dto.request.custom.Request;
import dto.response.generic.ResponseList;
import it.pathfinder.rollerbot.data.entity.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class DefaultController extends BasicController implements DaoController {

    private Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Override
    public GenericDTO set(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public GenericDTO reset(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public GenericDTO delete(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public GenericDTO get(ServerRequest serverRequest) {
        Request request = readRequest(serverRequest);
        Optional<Default> defaultOptional = defaultService.get(request.getName());
        return defaultOptional.<GenericDTO>map(DefaultDetail::new)
                .orElseGet(() ->
                        new Error("Default formula: " + request.getName() + " not found."));
    }

    @Override
    public GenericDTO list(ServerRequest serverRequest) {
        Optional<List<Default>> defaultList = defaultService.findAll();

        return defaultList.map(defaults ->
                new ResponseList(defaults.stream().map(DefaultDetail::new).collect(Collectors.toList())))
                .orElseGet(ResponseList::new);
    }

}
