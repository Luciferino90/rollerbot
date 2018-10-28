package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.Default;
import it.pathfinder.rollerbot.data.repository.DefaultRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultService {

    @Autowired
    private DefaultRepository defaultRepository;

    public Optional<Default> get(String name) {
        return Optional.of(defaultRepository.findDefaultByName(name));
    }

    public Optional<List<Default>> findAll() {
        return Optional.of(IteratorUtils.toList(defaultRepository.findAll().iterator()));
    }

}
