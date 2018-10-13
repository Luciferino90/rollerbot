package it.pathfinder.rollerbot.dao.repository;

import it.pathfinder.rollerbot.dao.entity.Levels;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelsRepository extends PagingAndSortingRepository<Levels, Long> {

}
