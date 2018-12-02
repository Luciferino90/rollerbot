package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.Default;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefaultRepository extends PagingAndSortingRepository<Default, Long> {

    Optional<Default> findDefaultByName(String name);

}
