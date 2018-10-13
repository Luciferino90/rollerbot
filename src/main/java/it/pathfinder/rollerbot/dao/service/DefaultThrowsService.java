package it.pathfinder.rollerbot.dao.service;

import it.pathfinder.rollerbot.dao.repository.DefaultThrowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultThrowsService {

    @Autowired
    private DefaultThrowsRepository defaultThrowsRepository;

}
