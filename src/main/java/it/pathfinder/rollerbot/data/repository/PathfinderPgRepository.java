package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathfinderPgRepository extends PagingAndSortingRepository<PathfinderPg, Long> {

    PathfinderPg findByNameAndTelegramUser(String name, TelegramUser telegramUser);

    List<PathfinderPg> findAllByTelegramUser(TelegramUser telegramUser);

}
