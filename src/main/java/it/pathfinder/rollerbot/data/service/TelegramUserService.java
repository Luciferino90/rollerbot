package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.Optional;

@Service
public class TelegramUserService {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    public TelegramUser findOrRegister(User user)
    {
        Optional<TelegramUser> telegramUser = findByUser(user);
        return telegramUser.orElse(registerUser(user));
    }

    public Optional<TelegramUser> findByUser(User user)
    {
        if (user.getId() != null)
            return telegramUserRepository.findTelegramUserByTgId(user.getId().longValue());
        else if (!StringUtils.isEmpty(user.getUserName()))
            return telegramUserRepository.findTelegramUserByTgUsername(user.getUserName());
        else
            return null;
    }

    public Optional<TelegramUser> findByTgOid(Long tgOid)
    {
        return telegramUserRepository.findTelegramUserByTgId(tgOid);
    }

    public TelegramUser registerUser(User user)
    {
        TelegramUser telegramUser = telegramUserRepository.findTelegramUserByTgId(Long.valueOf(user.getId()))
                .orElse(createNewUser(user));

        telegramUser.setTgName(user.getFirstName());
        telegramUser.setTgSurname(user.getLastName());
        telegramUser.setTgUsername(user.getUserName());
        return telegramUserRepository.save(telegramUser);
    }

    private TelegramUser createNewUser(User user)
    {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setRegisterDate(new Date());
        telegramUser.setTgId(user.getId().longValue());
        return telegramUser;
    }

    public TelegramUser createAnonUser()
    {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setTgUsername("anonymous");
        return telegramUser;
    }
}
