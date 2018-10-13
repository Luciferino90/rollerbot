package it.pathfinder.rollerbot.dao.repository;

import it.pathfinder.rollerbot.dao.entity.CustomVariables;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomVariablesRepository extends PagingAndSortingRepository<CustomVariables, Long> {

}
