package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.repository.CustomVariablesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomVariablesService {

    @Autowired
    private CustomVariablesRepository customVariablesRepository;

}
