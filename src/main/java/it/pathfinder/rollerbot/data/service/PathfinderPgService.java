package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.Stats;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.PathfinderPgRepository;
import it.pathfinder.rollerbot.exception.PathfinderPgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Objects;

@Service
public class PathfinderPgService {

    @Autowired
    private PathfinderPgRepository pathfinderPgRepository;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private CustomService customService;

    @Autowired
    private StatsService statsService;

    public Mono<PathfinderPg> set(String username, TelegramUser user) {
        return save(PathfinderPg.builder().name(username).telegramUser(user).build())
                .map(pathfinderPg -> {
                    if (user.getDefaultPathfinderPg() == null)
                        telegramUserService.setDefault(user, pathfinderPg);
                    return pathfinderPg;
                });
    }

    public Mono<PathfinderPg> reset(String username, TelegramUser user) {
        return findByNameAndTelegramUser(username, user)
                .map(pathfinderPg -> {
                    statsService.findByCharacter(pathfinderPg)
                            .map(stats -> {
                                stats.init();
                                return stats;
                            })
                            .flatMap(stats -> statsService.save(stats));
                    return pathfinderPg;
                })
                .switchIfEmpty(set(username, user));
    }

    private Mono<PathfinderPg> save(PathfinderPg pathfinderPg) {
        return Mono.just(pathfinderPgRepository.save(pathfinderPg))
                .map(pathfinderPgSaved -> Tuples.of(pathfinderPgSaved, statsService.save(new Stats(pathfinderPgSaved))))
                .map(Tuple2::getT1);
    }

    public Mono<PathfinderPg> findByOid(Long oid) {
        return Mono.just(Objects.requireNonNull(pathfinderPgRepository.findById(oid).orElse(null)))
                .switchIfEmpty(Mono.error(new PathfinderPgException("No pathfinder character by id " + oid)));
    }

    public Mono<PathfinderPg> findByNameAndTelegramUser(String name, TelegramUser telegramUser) {
        return Mono.just(Objects.requireNonNull(pathfinderPgRepository.findByNameAndTelegramUser(name, telegramUser).orElse(null)))
                .switchIfEmpty(Mono.error(new PathfinderPgException("No pathfinder character by name " + name)));
    }

    public Mono<PathfinderPg> findByNameAndTelegramUserOrEmpty(String name, TelegramUser telegramUser) {
        return Mono.justOrEmpty(pathfinderPgRepository.findByNameAndTelegramUser(name, telegramUser));
    }

    public Mono<PathfinderPg> delete(String name, TelegramUser telegramUser) {
        return findByNameAndTelegramUser(name, telegramUser)
                .map(pathfinderPg -> {
                    if (telegramUser.getDefaultPathfinderPg() != null &&
                            telegramUser.getDefaultPathfinderPg().getId().equals(pathfinderPg.getId()))
                        telegramUserService.setDefault(telegramUser, null);
                    return pathfinderPg;
                })
                .map(pathfinderPg -> {
                    customService.findByPathfinderPg(pathfinderPg)
                            .map(custom -> customService.delete(custom));
                    return pathfinderPg;
                })
                .map(pathfinderPg -> {
                    statsService.findByCharacter(pathfinderPg)
                            .map(stats -> statsService.delete(stats));
                    return pathfinderPg;
                })
                .map(pathfinderPg -> {
                    pathfinderPgRepository.delete(pathfinderPg);
                    return pathfinderPg;
                });
    }

    public Flux<PathfinderPg> list(TelegramUser telegramUser) {
        return Flux.fromIterable(pathfinderPgRepository.findAllByTelegramUser(telegramUser));
    }

}
