package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.Stats;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.PathfinderPgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathfinderPgService {

    @Autowired
    private PathfinderPgRepository pathfinderPgRepository;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private CustomService customService;

    @Autowired
    private StatsService statsService;

    public PathfinderPg set(String username, TelegramUser user) {
        PathfinderPg pathfinderPg = new PathfinderPg();
        pathfinderPg.setName(username);
        pathfinderPg.setTelegramUser(user);
        pathfinderPg = save(pathfinderPg);
        if (user.getDefaultPathfinderPg() == null)
            telegramUserService.setDefault(user, pathfinderPg);
        statsService.save(new Stats(pathfinderPg));
        return pathfinderPg;
    }

    public PathfinderPg reset(String username, TelegramUser user) {
        PathfinderPg pathfinderPg = findByNameAndTelegramUser(username, user);
        if (pathfinderPg == null)
            return set(username, user);
        else {
            Stats stat = statsService.findByCharacter(pathfinderPg);
            if (stat == null)
                stat = new Stats(pathfinderPg);
            stat.init();
            statsService.save(stat);
        }
        return pathfinderPg;
    }

    private PathfinderPg save(PathfinderPg pathfinderPg) {
        return pathfinderPgRepository.save(pathfinderPg);
    }

    public PathfinderPg findByOid(Long oid) {
        return pathfinderPgRepository.findById(oid).orElse(null);
    }

    public PathfinderPg findByNameAndTelegramUser(String name, TelegramUser telegramUser) {
        return pathfinderPgRepository.findByNameAndTelegramUser(name, telegramUser);
    }

    public PathfinderPg delete(String name, TelegramUser telegramUser) {
        PathfinderPg pathfinderPg = findByNameAndTelegramUser(name, telegramUser);
        if (telegramUser.getDefaultPathfinderPg() != null &&
                telegramUser.getDefaultPathfinderPg().getId().equals(pathfinderPg.getId()))
            telegramUserService.setDefault(telegramUser, null);
        if (customService.findByPathfinderPg(pathfinderPg).isPresent())
            customService.findByPathfinderPg(pathfinderPg).get().forEach(cs -> customService.delete(cs));
        pathfinderPgRepository.delete(pathfinderPg);
        return pathfinderPg;
    }

    public List<PathfinderPg> list(TelegramUser telegramUser) {
        return pathfinderPgRepository.findAllByTelegramUser(telegramUser);
    }

}
