package it.pathfinder.rollerbot.dao.service;

import it.pathfinder.rollerbot.dao.repository.CustomVariablesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomVariablesService {

    @Autowired
    private CustomVariablesRepository customVariablesRepository;

}
