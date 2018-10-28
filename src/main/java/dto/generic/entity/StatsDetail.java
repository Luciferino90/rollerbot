package dto.generic.entity;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.Stats;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatsDetail extends GenericDTO implements Serializable {

    private Stats stats;

    public StatsDetail(Stats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return String.format("Player: %s - Character: %s\n" +
                        "HP: %s\n" +
                        "STR: %s\n" +
                        "DEX: %s\n" +
                        "COS: %s\n" +
                        "CHA: %s\n" +
                        "INT: %s\n" +
                        "WIS: %s\n" +
                        "FOR: %s\n" +
                        "WIL: %s\n" +
                        "REF: %s\n" +
                        "BAB: %s\n" +
                        "LVL: %s\n" +
                        "INIT: %s\n" +
                        "AC: %s\n" +
                        "SAC: %s\n" +
                        "CAC: %s\n", stats.getPathfinderPg().getTelegramUser().getTgUsername(), stats.getPathfinderPg().getName(),
                stats.getHp(), stats.getAsStrength(), stats.getAsDextery(), stats.getAsConstitution(), stats.getAsCharisma(),
                stats.getAsIntelligence(), stats.getAsWisdom(), stats.getTsReflex(), stats.getTsFortitude(), stats.getTsReflex(),
                stats.getBaseAttackBonus(), stats.getLevel(), stats.getInit(), stats.getArmorClass(), stats.getSurpriseArmorClass(),
                stats.getContactArmorClass());
    }

}
