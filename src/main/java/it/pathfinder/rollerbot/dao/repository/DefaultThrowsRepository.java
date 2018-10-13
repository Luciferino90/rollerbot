package it.pathfinder.rollerbot.dao.repository;

import it.pathfinder.rollerbot.dao.entity.DefaultThrows;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultThrowsRepository extends PagingAndSortingRepository<DefaultThrows, Long> {

}
