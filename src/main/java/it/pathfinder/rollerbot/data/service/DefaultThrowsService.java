package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.repository.DefaultThrowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultThrowsService {

    @Autowired
    private DefaultThrowsRepository defaultThrowsRepository;

}
