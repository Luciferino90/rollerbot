package it.pathfinder.rollerbot.data.repository;

import it.pathfinder.rollerbot.data.entity.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends PagingAndSortingRepository<TelegramUser, Long>
{

    TelegramUser findTelegramUserByTgId(Long tgId);
    TelegramUser findTelegramUserByTgUsername(String tgUsername);

}
