package it.pathfinder.rollerbot.dao.service;

import it.pathfinder.rollerbot.dao.entity.TelegramUser;
import it.pathfinder.rollerbot.dao.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;

@Service
public class TelegramUserService {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    public TelegramUser findOrRegister(User user)
    {
        TelegramUser telegramUser = findByUser(user);
        return telegramUser != null ? telegramUser : registerUser(user);
    }

    public TelegramUser findByUser(User user)
    {
        if (user.getId() != null)
            return telegramUserRepository.findTelegramUserByTgId(user.getId().longValue());
        else if (!StringUtils.isEmpty(user.getUserName()))
            return telegramUserRepository.findTelegramUserByTgUsername(user.getUserName());
        else
            return null;
    }

    public TelegramUser registerUser(User user)
    {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setRegisterDate(new Date());
        telegramUser.setTgId(user.getId().longValue());
        telegramUser.setTgName(user.getFirstName());
        telegramUser.setTgSurname(user.getLastName());
        telegramUser.setTgUsername(user.getUserName());
        return telegramUserRepository.save(telegramUser);
    }
}
