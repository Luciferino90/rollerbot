package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.Stats;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.StatsRepository;
import it.pathfinder.rollerbot.exception.StatsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private PathfinderPgService pathfinderPgService;

    public Optional<Stats> findByCharacter(PathfinderPg pathfinderPg) {
        return statsRepository.findAllByPathfinderPg(pathfinderPg);
    }

    public List<Stats> list(TelegramUser telegramUser) {
        return pathfinderPgService.list(telegramUser)
                .stream().map(pathfinderPg -> statsRepository.findAllByPathfinderPg(pathfinderPg)
                    .orElseThrow(() -> new StatsException("No stats found for " + pathfinderPg.getName())))
                .collect(Collectors.toList());
    }

    public Stats save(Stats stats) {
        return statsRepository.save(stats);
    }

    public Stats set(PathfinderPg pathfinderPg, String name, Integer value) {
        Stats stat = statsRepository.findAllByPathfinderPg(pathfinderPg).orElse(new Stats(pathfinderPg));
        switch (name.toUpperCase()) {
            case "HP":
                stat.setHp(value);
                break;
            case "STR":
                stat.setAsStrength(value);
                break;
            case "DEX":
                stat.setAsDextery(value);
                break;
            case "COS":
                stat.setAsConstitution(value);
                break;
            case "CHA":
                stat.setAsCharisma(value);
                break;
            case "INT":
                stat.setAsIntelligence(value);
                break;
            case "WIS":
                stat.setAsWisdom(value);
                break;
            case "FOR":
                stat.setTsFortitude(value);
                break;
            case "WIL":
                stat.setTsWill(value);
                break;
            case "REF":
                stat.setTsReflex(value);
                break;
            case "BAB":
                stat.setBaseAttackBonus(value);
                break;
            case "LVL":
                stat.setLevel(value);
                break;
            case "INIT":
                stat.setInit(value);
                break;
            case "AC":
                stat.setArmorClass(value);
                break;
            case "SAC":
                stat.setSurpriseArmorClass(value);
                break;
            case "CAC":
                stat.setContactArmorClass(value);
                break;
        }
        return statsRepository.save(stat);
    }

    public Integer get(PathfinderPg pathfinderPg, String name) {
        Stats stat = statsRepository.findAllByPathfinderPg(pathfinderPg).orElse(new Stats());
        switch (name.toUpperCase()) {
            case "HP":
                return stat.getHp();
            case "STR":
                return stat.getAsStrength();
            case "DEX":
                return stat.getAsDextery();
            case "COS":
                return stat.getAsConstitution();
            case "CHA":
                return stat.getAsCharisma();
            case "INT":
                return stat.getAsIntelligence();
            case "WIS":
                return stat.getAsWisdom();
            case "FOR":
                return stat.getTsFortitude();
            case "WIL":
                return stat.getTsWill();
            case "REF":
                return stat.getTsReflex();
            case "BAB":
                return stat.getBaseAttackBonus();
            case "LVL":
                return stat.getLevel();
            case "INIT":
                return stat.getInit();
            case "AC":
                return stat.getArmorClass();
            case "SAC":
                return stat.getSurpriseArmorClass();
            case "CAC":
                return stat.getContactArmorClass();
            default:
                return 0;
        }
    }


}
