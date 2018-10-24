package it.pathfinder.rollerbot.webflux.handler;

import dto.generic.Error;
import dto.generic.entity.CustomThrowsDetail;
import dto.generic.response.customthrows.CustomThrowsDetailList;
import dto.request.customthrows.CustomThrowsRequest;
import it.pathfinder.rollerbot.data.entity.CustomThrows;
import it.pathfinder.rollerbot.data.service.CustomThrowsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
public class StatsHandler extends BasicHandler implements DaoHandler {

    private Logger logger = LoggerFactory.getLogger(StatsHandler.class);

    @Autowired
    private CustomThrowsService customThrowsService;

    @Override
    public Mono<ServerResponse> set(ServerRequest serverRequest)
    {
        CustomThrowsRequest customThrowsRequest = readRequest(serverRequest);
        if (customThrowsRequest.getCustomName() == null || customThrowsRequest.getCustomFormula() == null)
            return response(new Error("Insufficent parameters."));
        Optional<CustomThrows> customThrowsOptional = customThrowsService.setIfNotExists(customThrowsRequest.getTgOid(),
                customThrowsRequest.getCustomName(), customThrowsRequest.getCustomFormula());

        if (customThrowsOptional.isPresent())
            return response(new CustomThrowsDetail(customThrowsOptional.get()));
        else
            return response(new Error("CustomFormula already present: " + customThrowsRequest.getCustomName() + ". Use reset to overwrite, or delete to remove it."));
    }

    @Override
    public Mono<ServerResponse> reset(ServerRequest serverRequest)
    {
        CustomThrowsRequest customThrowsRequest = readRequest(serverRequest);
        if (customThrowsRequest.getCustomName() == null || customThrowsRequest.getCustomFormula() == null)
            return response(new Error("Insufficent parameters."));
        Optional<CustomThrows> customThrowsOptional = customThrowsService.overwriteIfExists(customThrowsRequest.getTgOid(),
                customThrowsRequest.getCustomName(), customThrowsRequest.getCustomFormula());

        if (customThrowsOptional.isPresent())
            return response(new CustomThrowsDetail(customThrowsOptional.get()));
        else
            return response(new Error("CustomFormula not registered: " + customThrowsRequest.getCustomName()+ ". Please try again later."));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest)
    {
        CustomThrowsRequest customThrowsRequest = readRequest(serverRequest);
        if (customThrowsRequest.getCustomName() == null)
            return response(new Error("Insufficent parameters."));
        Optional<CustomThrows> customThrowsOptional = customThrowsService.delete(customThrowsRequest.getTgOid(), customThrowsRequest.getCustomName());

        if (customThrowsOptional.isPresent())
            return response(new CustomThrowsDetail(customThrowsOptional.get()));
        else
            return response(new Error("CustomFormula delete failed: " + customThrowsRequest.getCustomName() + ". Please try again later."));
    }

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest)
    {
        return Mono.empty();
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest serverRequest)
    {
        CustomThrowsRequest customThrowsRequest = readRequest(serverRequest);
        Optional<List<CustomThrows>> customThrowsList = customThrowsService.findByUser(customThrowsRequest.getTgOid());
        if(customThrowsList.isPresent()){
            CustomThrowsDetailList customThrowsDetailList = new CustomThrowsDetailList();
            customThrowsDetailList.convertCustomThrowsList(customThrowsList.get());
            return response(customThrowsDetailList);
        } else
            return response(new CustomThrowsDetailList());
    }

    private CustomThrowsRequest readRequest(ServerRequest serverRequest)
    {
        Long tgOid = Long.parseLong(serverRequest.queryParam("tgOid").orElse(""));
        String formulaName = serverRequest.pathVariables().containsKey("formulaName") ? serverRequest.pathVariable("formulaName") : null;
        String formulaValue = serverRequest.pathVariables().containsKey("formulaValue") ? serverRequest.pathVariable("formulaValue") : null;
        return new CustomThrowsRequest(tgOid, formulaName, formulaValue);
    }

}
