package it.pathfinder.rollerbot.dao.service;

import it.pathfinder.rollerbot.dao.entity.PathfinderPg;
import it.pathfinder.rollerbot.dao.entity.TelegramUser;
import it.pathfinder.rollerbot.dao.repository.PathfinderPgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathfinderPgService {

    @Autowired
    private PathfinderPgRepository pathfinderPgRepository;

    public PathfinderPg create(String username, TelegramUser user)
    {
        PathfinderPg pathfinderPg = new PathfinderPg();
        pathfinderPg.setName(username);
        pathfinderPg.setTelegramUser(user);
        return save(pathfinderPg);
    }

    public PathfinderPg save(PathfinderPg pathfinderPg)
    {
        return pathfinderPgRepository.save(pathfinderPg);
    }

    public PathfinderPg findByOid(Long oid)
    {
        return pathfinderPgRepository.findById(oid).orElse(null);
    }

}
