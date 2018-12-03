package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.TelegramUserRepository;
import it.pathfinder.rollerbot.exception.TelegramUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

@Service
public class TelegramUserService {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    public Mono<TelegramUser> findOrRegister(User user) {
        return findByUser(user)
                .switchIfEmpty(registerUser(user));
    }

    public Mono<TelegramUser> findByUser(User user) {
        return Mono.just(user)
                .flatMap(u ->
                        u.getId() != null
                                ? telegramUserRepository.findTelegramUserByTgId(user.getId().longValue())
                                : !StringUtils.isEmpty(u.getUserName())
                                    ? telegramUserRepository.findTelegramUserByTgUsername(user.getUserName())
                                        : Mono.empty()
                );
    }

    public Mono<TelegramUser> findByTgOid(Long tgOid) {
        return telegramUserRepository.findTelegramUserByTgId(tgOid)
                .switchIfEmpty(Mono.error(new TelegramUserException("No telegram user found for " + tgOid)));
    }

    public Mono<TelegramUser> registerUser(User user) {
        return telegramUserRepository.findTelegramUserByTgId(Long.valueOf(user.getId())).switchIfEmpty(createNewUser(user))
            .map(telegramUser -> TelegramUser.builder().tgName(user.getFirstName()).tgSurname(user.getLastName()).tgUsername(user.getUserName()).build())
                .flatMap(telegramUser -> telegramUserRepository.save(telegramUser));
    }

    private Mono<TelegramUser> createNewUser(User user) {
        return Mono.just(new TelegramUser())
                .map(telegramUser -> TelegramUser.builder().registerDate(new Date()).tgId(user.getId().longValue()).build());
    }

    public Mono<TelegramUser> createAnonUser() {
        return Mono.just(new TelegramUser())
                .map(telegramUser -> TelegramUser.builder().tgUsername("anonymous").build());
    }

    public Mono<TelegramUser> setDefault(TelegramUser telegramUser, PathfinderPg pathfinderPg) {
        return Mono.just(telegramUser)
                .flatMap(tgUser -> {
                    telegramUser.setDefaultPathfinderPg(pathfinderPg);
                    return telegramUserRepository.save(telegramUser);
                });
    }

}
