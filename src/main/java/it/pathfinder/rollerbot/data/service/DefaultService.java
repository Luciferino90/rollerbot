package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.Default;
import it.pathfinder.rollerbot.data.repository.DefaultRepository;
import it.pathfinder.rollerbot.exception.DefaultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class DefaultService {

    @Autowired
    private DefaultRepository defaultRepository;

    public Mono<Default> get(String name) {
        return Mono.just(Objects.requireNonNull(defaultRepository.findDefaultByName(name).orElse(null)))
                .switchIfEmpty(Mono.error(new DefaultException("Default formula: " + name + " not found.")));
    }

    public Flux<Default> findAll() {
        return Flux.fromIterable(defaultRepository.findAll())
                .switchIfEmpty(Flux.error(new DefaultException("No default found")));
    }

}
