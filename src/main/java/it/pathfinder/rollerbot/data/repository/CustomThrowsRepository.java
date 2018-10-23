package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.CustomThrows;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomThrowsRepository extends PagingAndSortingRepository<CustomThrows, Long> {

    Optional<CustomThrows> findByCustomNameAndTelegramUser(String customName, TelegramUser telegramUser);
    Optional<List<CustomThrows>> findByTelegramUser(TelegramUser telegramUser);
}
