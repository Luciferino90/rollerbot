package it.pathfinder.rollerbot.dao.service;

import it.pathfinder.rollerbot.dao.repository.CustomThrowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomThrowsService {

    @Autowired
    private CustomThrowsRepository customThrowsRepository;

}
