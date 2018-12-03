package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomRepository extends ReactiveSortingRepository<Custom, Long> {

    Mono<Custom> findByCustomNameAndTelegramUser(String customName, TelegramUser telegramUser);

    Mono<Custom> findByCustomNameAndTelegramUserAndPathfinderPg(String customName, TelegramUser telegramUser,
                                                                PathfinderPg pathfinderPg);

    Flux<Custom> findByTelegramUser(TelegramUser telegramUser);

    Flux<Custom> findByPathfinderPg(PathfinderPg pathfinderPg);
}
