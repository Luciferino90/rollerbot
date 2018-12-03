package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.Stats;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatsRepository extends PagingAndSortingRepository<Stats, Long> {

    Optional<Stats> findAllByPathfinderPg(PathfinderPg pathfinderPg);

}
