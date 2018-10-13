package it.pathfinder.rollerbot.dao.repository;

import it.pathfinder.rollerbot.dao.entity.Stats;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends PagingAndSortingRepository<Stats, Long> {

}
