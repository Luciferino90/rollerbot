package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.repository.CustomThrowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomThrowsService {

    @Autowired
    private CustomThrowsRepository customThrowsRepository;

}
