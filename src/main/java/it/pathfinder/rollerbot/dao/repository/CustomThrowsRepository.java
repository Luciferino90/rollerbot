package it.pathfinder.rollerbot.dao.repository;

import it.pathfinder.rollerbot.dao.entity.CustomThrows;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomThrowsRepository extends PagingAndSortingRepository<CustomThrows, Long> {

}
