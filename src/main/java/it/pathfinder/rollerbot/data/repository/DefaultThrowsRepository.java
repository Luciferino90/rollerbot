package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.DefaultThrows;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultThrowsRepository extends PagingAndSortingRepository<DefaultThrows, Long> {

}
