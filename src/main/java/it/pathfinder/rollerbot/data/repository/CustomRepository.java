package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomRepository extends PagingAndSortingRepository<Custom, Long> {

    Optional<Custom> findByCustomNameAndTelegramUser(String customName, TelegramUser telegramUser);

    Optional<Custom> findByCustomNameAndTelegramUserAndPathfinderPg(String customName, TelegramUser telegramUser,
                                                                PathfinderPg pathfinderPg);

    List<Custom> findByTelegramUser(TelegramUser telegramUser);

    List<Custom> findByPathfinderPg(PathfinderPg pathfinderPg);

}
