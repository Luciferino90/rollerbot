package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface PathfinderPgRepository extends PagingAndSortingRepository<PathfinderPg, Long> {

    Optional<PathfinderPg> findByNameAndTelegramUser(String name, TelegramUser telegramUser);

    List<PathfinderPg> findAllByTelegramUser(TelegramUser telegramUser);

}
