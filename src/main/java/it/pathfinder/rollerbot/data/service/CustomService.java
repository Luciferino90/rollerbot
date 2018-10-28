package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomService {

    @Autowired
    private CustomRepository customRepository;

    @Autowired
    private TelegramUserService telegramUserService;

    public Optional<Custom> findByUserAndCustomName(TelegramUser telegramUser, String customName, PathfinderPg pathfinderPg) {
        return customRepository.findByCustomNameAndTelegramUserAndPathfinderPg(customName, telegramUser, pathfinderPg);
    }

    public Optional<Custom> setIfNotExists(Long tgOid, String customName, String customValue) {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        if (Objects.requireNonNull(telegramUser).getDefaultPathfinderPg() == null)
            return Optional.empty();
        if (findByUserAndCustomName(telegramUser, customName, telegramUser.getDefaultPathfinderPg()).isPresent())
            return Optional.empty();


        Custom custom = new Custom();
        custom.setTelegramUser(telegramUser);
        custom.setPathfinderPg(telegramUser.getDefaultPathfinderPg());
        custom.setCustomName(customName);
        custom.setCustomValue(customValue);
        return Optional.of(customRepository.save(custom));
    }

    public Optional<Custom> overwriteIfExists(Long tgOid, String customName, String customValue) {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        if (Objects.requireNonNull(telegramUser).getDefaultPathfinderPg() == null)
            return Optional.empty();
        Custom custom = findByUserAndCustomName(telegramUser, customName, telegramUser.getDefaultPathfinderPg())
                .orElse(new Custom());

        custom.setTelegramUser(telegramUser);
        custom.setCustomName(customName);
        custom.setCustomValue(customValue);
        return Optional.of(customRepository.save(custom));
    }

    public Optional<Custom> delete(Custom custom) {
        customRepository.delete(custom);
        return Optional.of(custom);
    }

    public Optional<Custom> delete(Long tgOid, String customName) {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        if (Objects.requireNonNull(telegramUser).getDefaultPathfinderPg() == null)
            return Optional.empty();

        Optional<Custom> customThrows = findByUserAndCustomName(telegramUser, customName, telegramUser.getDefaultPathfinderPg());
        customThrows.ifPresent(customThrows1 -> customRepository.delete(customThrows1));
        return customThrows;
    }

    public Optional<List<Custom>> findByUserOid(Long tgOid) {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        return Optional.of(customRepository.findByTelegramUser(telegramUser));
    }

    public Optional<List<Custom>> findByPathfinderPg(PathfinderPg pathfinderPg) {
        return Optional.of(customRepository.findByPathfinderPg(pathfinderPg));
    }

}
