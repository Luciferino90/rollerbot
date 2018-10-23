package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.CustomThrows;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.CustomThrowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomThrowsService {

    @Autowired
    private CustomThrowsRepository customThrowsRepository;

    @Autowired
    private TelegramUserService telegramUserService;

    public Optional<CustomThrows> findByCustomNameAndUser(TelegramUser telegramUser, String customName)
    {
        return customThrowsRepository.findByCustomNameAndTelegramUser(customName, telegramUser);
    }

    public Optional<CustomThrows> setIfNotExists(Long tgOid, String customName, String customValue)
    {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        if (findByCustomNameAndUser(telegramUser, customName).isPresent())
            return Optional.empty();

        CustomThrows customThrows = new CustomThrows();
        customThrows.setTelegramUser(telegramUser);
        customThrows.setCustomName(customName);
        customThrows.setCustomCommand(customValue);
        return Optional.of(customThrowsRepository.save(customThrows));
    }

    public Optional<CustomThrows> overwriteIfExists(Long tgOid, String customName, String customValue)
    {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        CustomThrows customThrows = findByCustomNameAndUser(telegramUser, customName).orElse(new CustomThrows());

        customThrows.setTelegramUser(telegramUser);
        customThrows.setCustomName(customName);
        customThrows.setCustomCommand(customValue);
        return Optional.of(customThrowsRepository.save(customThrows));
    }

    public Optional<CustomThrows> delete(Long tgOid, String customName)
    {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        Optional<CustomThrows> customThrows = findByCustomNameAndUser(telegramUser, customName);
        customThrows.ifPresent(customThrows1 -> customThrowsRepository.delete(customThrows1));
        return customThrows;
    }

    public Optional<List<CustomThrows>> findByUser(Long tgOid)
    {
        TelegramUser telegramUser = telegramUserService.findByTgOid(tgOid).orElse(null);
        return customThrowsRepository.findByTelegramUser(telegramUser);
    }

}
