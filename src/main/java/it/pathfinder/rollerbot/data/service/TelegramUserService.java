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

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

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
                                ? Mono.just(Objects.requireNonNull(telegramUserRepository.findTelegramUserByTgId(user.getId().longValue()).orElse(null)))
                                : !StringUtils.isEmpty(u.getUserName())
                                    ? Mono.just(Objects.requireNonNull(telegramUserRepository.findTelegramUserByTgUsername(user.getUserName()).orElse(null)))
                                        : Mono.empty()
                );
    }

    public Mono<TelegramUser> findByTgOid(Long tgOid) {
        return Mono.just(Objects.requireNonNull(telegramUserRepository.findTelegramUserByTgId(tgOid).orElse(null)))
                .switchIfEmpty(Mono.error(new TelegramUserException("No telegram user found for " + tgOid)));
    }

    @Transactional
    public Mono<TelegramUser> registerUser(User user) {
        return findTelegramUserByTgId(Long.valueOf(user.getId()))
                .switchIfEmpty(createNewUser(user));
    }

    private Mono<TelegramUser> createNewUser(User user) {
        return Mono.just(new TelegramUser())
                .map(telegramUser -> TelegramUser
                        .builder()
                        .tgName(user.getFirstName())
                        .tgSurname(user.getLastName())
                        .tgUsername(user.getUserName())
                        .registerDate(new Date())
                        .build())
                .map(telegramUser -> telegramUserRepository.save(telegramUser));
    }

    public Mono<TelegramUser> createAnonUser() {
        return Mono.just(new TelegramUser())
                .map(telegramUser -> TelegramUser.builder().tgUsername("anonymous").build());
    }

    public Mono<TelegramUser> setDefault(TelegramUser telegramUser, PathfinderPg pathfinderPg) {
        return Mono.just(telegramUser)
                .flatMap(tgUser -> {
                    telegramUser.setDefaultPathfinderPg(pathfinderPg);
                    return Mono.just(telegramUserRepository.save(telegramUser));
                });
    }

    public Mono<TelegramUser> findTelegramUserByTgId(Long id){
        return Mono.justOrEmpty(telegramUserRepository.findTelegramUserByTgId(id));
    }

}
