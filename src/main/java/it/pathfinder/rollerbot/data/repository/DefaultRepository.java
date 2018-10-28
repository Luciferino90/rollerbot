package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.Default;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultRepository extends PagingAndSortingRepository<Default, Long> {

    Default findDefaultByName(String name);

}
