package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends PagingAndSortingRepository<TelegramUser, Long> {

    Optional<TelegramUser> findTelegramUserByTgId(Long tgId);

    Optional<TelegramUser> findTelegramUserByTgUsername(String tgUsername);

}
