package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.Custom;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.CustomRepository;
import it.pathfinder.rollerbot.exception.TelegramUserException;
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

    public Optional<Custom> findByUserAndCustomNameAndPathfinderPg(TelegramUser telegramUser, String customName, PathfinderPg pathfinderPg) {
        return customRepository.findByCustomNameAndTelegramUserAndPathfinderPg(customName, telegramUser, pathfinderPg);
    }

    public Optional<Custom> setIfNotExists(Long tgOid, String customName, String customValue) {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        if (Objects.requireNonNull(telegramUser).getDefaultPathfinderPg() == null)
            return Optional.empty();
        if (findByUserAndCustomNameAndPathfinderPg(telegramUser, customName, telegramUser.getDefaultPathfinderPg()).isPresent())
            return Optional.empty();


        Custom custom = new Custom();
        custom.setTelegramUser(telegramUser);
        custom.setPathfinderPg(telegramUser.getDefaultPathfinderPg());
        custom.setCustomName(customName);
        custom.setCustomValue(customValue);
        return Optional.of(customRepository.save(custom));
    }

    public Optional<Custom> overwriteIfExists(Long tgOid, String customName, String customValue) {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElseThrow(() -> new TelegramUserException("No telegram user found for " + tgOid));

        Custom custom = findByUserAndCustomNameAndPathfinderPg(telegramUser, customName, telegramUser.getDefaultPathfinderPg())
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
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElseThrow(() -> new TelegramUserException("No telegram user found for " + tgOid));
        Custom customThrows = findByUserAndCustomNameAndPathfinderPg(telegramUser, customName, telegramUser.getDefaultPathfinderPg())
                .orElseThrow(()-> new TelegramUserException("Custom not found for " + customName + " and " + telegramUser.getDefaultPathfinderPg().getName()));
        customRepository.delete(customThrows);

        return Optional.of(customThrows);
    }

    public Optional<List<Custom>> findByUserOid(Long tgOid) {
        return customRepository.findByTelegramUser(telegramUserService.findByTgOid(tgOid).orElseThrow(()-> new TelegramUserException("User not found: " + tgOid)));
    }

    public Optional<List<Custom>> findByPathfinderPg(PathfinderPg pathfinderPg) {
        return customRepository.findByPathfinderPg(pathfinderPg);
    }

}
