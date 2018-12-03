package it.pathfinder.rollerbot.data.service;

import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import it.pathfinder.rollerbot.data.entity.Stats;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.data.repository.StatsRepository;
import it.pathfinder.rollerbot.exception.StatsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private PathfinderPgService pathfinderPgService;

    public Mono<Stats> findByCharacter(PathfinderPg pathfinderPg) {
        return Mono.just(Objects.requireNonNull(statsRepository.findAllByPathfinderPg(pathfinderPg).orElse(null)))
                .switchIfEmpty(Mono.error(new StatsException("No stats found for " + pathfinderPg.getName())));
    }

    public Flux<Stats> list(TelegramUser telegramUser) {
        return pathfinderPgService.list(telegramUser)
                .flatMap(pathfinderPg -> Mono.just(Objects.requireNonNull(statsRepository.findAllByPathfinderPg(pathfinderPg).orElse(null)))
                    .switchIfEmpty(Mono.error(new StatsException("No stats found for " + pathfinderPg.getName()))));
    }

    public Mono<Stats> save(Stats stats) {
        return Mono.just(statsRepository.save(stats));
    }

    public Mono<Stats> set(PathfinderPg pathfinderPg, String name, Integer value) {
        return Mono.just(Objects.requireNonNull(statsRepository.findAllByPathfinderPg(pathfinderPg).orElse(null)))
                .defaultIfEmpty(new Stats(pathfinderPg))
            .map(stat -> {
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
                return stat;
            })
            .flatMap(stats -> Mono.just(statsRepository.save(stats)));
    }

    public Mono<Integer> get(PathfinderPg pathfinderPg, String name) {
        return Mono.just(Objects.requireNonNull(statsRepository.findAllByPathfinderPg(pathfinderPg).orElse(null)))
                .defaultIfEmpty(new Stats(pathfinderPg))
                .map(stat -> {
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
                });
        }

    public Mono<Stats> delete(Stats stats){
        return Mono.just(stats)
                .map(stat -> {
                    statsRepository.delete(stat);
                    return stat;
                });
    }

}
