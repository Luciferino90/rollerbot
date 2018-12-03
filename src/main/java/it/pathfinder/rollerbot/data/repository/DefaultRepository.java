package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.Default;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface DefaultRepository extends ReactiveSortingRepository<Default, Long> {

    Mono<Default> findDefaultByName(String name);

}
