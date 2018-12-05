package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.CustomRepository;
import it.pathfinder.rollerbot.exception.CustomException;
import it.pathfinder.rollerbot.exception.TelegramUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class CustomService {

    @Autowired
    private CustomRepository customRepository;

    @Autowired
    private TelegramUserService telegramUserService;

    public Mono<Custom> findByUserAndCustomNameAndPathfinderPg(TelegramUser telegramUser, String customName, PathfinderPg pathfinderPg) {
        return Mono.just(Objects.requireNonNull(customRepository.findByCustomNameAndTelegramUserAndPathfinderPg(customName, telegramUser, pathfinderPg).orElse(null)))
                .switchIfEmpty(Mono.error(new TelegramUserException("Custom not found for " + customName+ " and " + telegramUser.getDefaultPathfinderPg().getName())));
    }

    public Mono<Custom> setIfNotExists(Long tgOid, String customName, String customValue) {
        return telegramUserService.findByTgOid(tgOid)
            .map(telegramUser -> Tuples.of(telegramUser, findByUserAndCustomNameAndPathfinderPg(telegramUser, customName, telegramUser.getDefaultPathfinderPg())))
            .flatMap(tuple2 -> tuple2.getT2()
                    .switchIfEmpty(Mono.just(customRepository.save(
                            Custom.builder()
                                    .telegramUser(tuple2.getT1())
                                    .pathfinderPg(tuple2.getT1().getDefaultPathfinderPg())
                                    .customName(customName)
                                    .customValue(customValue)
                                    .build())))
            );
    }

    public Mono<Custom> overwriteIfExists(Long tgOid, String customName, String customValue) {
        return telegramUserService.findByTgOid(tgOid)
                .flatMap(telegramUser -> findByUserAndCustomNameAndPathfinderPg(telegramUser, customName, telegramUser.getDefaultPathfinderPg())
                        .defaultIfEmpty(Custom.builder().telegramUser(telegramUser).build()))
                    .map(custom -> {
                        custom.setCustomName(customName);
                        custom.setCustomValue(customValue);
                        return custom;
                    })
                    .flatMap(custom -> Mono.just(customRepository.save(custom)));
    }

    public Mono<Custom> delete(Custom custom) {
        return Mono.just(custom)
                .map(c -> {
                    customRepository.delete(c);
                    return c;
                });
    }

    public Mono<Custom> delete(Long tgOid, String customName) {
        return telegramUserService.findByTgOid(tgOid)
                .flatMap(telegramUser -> findByUserAndCustomNameAndPathfinderPg(telegramUser, customName, telegramUser.getDefaultPathfinderPg())
                            .switchIfEmpty(Mono.error(new TelegramUserException("Custom not found for " + customName + " and " + telegramUser.getDefaultPathfinderPg().getName()))))
                .flatMap(this::delete);
    }

    public Flux<Custom> findByUserOid(Long tgOid) {
        return telegramUserService.findByTgOid(tgOid)
                .map(telegramUser -> customRepository.findByTelegramUser(telegramUser))
                .switchIfEmpty(Mono.error(new CustomException("No custom command found for user " + tgOid)))
                .flatMapMany(customs -> Flux.fromStream(customs.stream()));
    }

    public Stream<Custom> findByPathfinderPg(PathfinderPg pathfinderPg) {
        return customRepository.findByPathfinderPg(pathfinderPg).stream();
    }

}
