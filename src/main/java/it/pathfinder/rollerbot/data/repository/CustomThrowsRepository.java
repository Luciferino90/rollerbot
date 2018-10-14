package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.CustomThrows;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomThrowsRepository extends PagingAndSortingRepository<CustomThrows, Long> {

}
