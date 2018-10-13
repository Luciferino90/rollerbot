package it.pathfinder.rollerbot.dao.repository;

import it.pathfinder.rollerbot.dao.entity.PathfinderPg;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathfinderPgRepository extends PagingAndSortingRepository<PathfinderPg, Long> {

}
