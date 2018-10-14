package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.CustomVariables;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomVariablesRepository extends PagingAndSortingRepository<CustomVariables, Long> {

}
