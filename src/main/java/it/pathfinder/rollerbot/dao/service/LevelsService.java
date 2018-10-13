package it.pathfinder.rollerbot.dao.service;

import it.pathfinder.rollerbot.dao.repository.LevelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelsService {

    @Autowired
    private LevelsRepository levelsRepository;

}
