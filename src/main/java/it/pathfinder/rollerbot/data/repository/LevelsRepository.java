package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.Levels;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelsRepository extends PagingAndSortingRepository<Levels, Long> {

}
