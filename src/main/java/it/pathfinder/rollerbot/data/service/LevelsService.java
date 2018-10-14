package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.repository.LevelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelsService {

    @Autowired
    private LevelsRepository levelsRepository;

}
