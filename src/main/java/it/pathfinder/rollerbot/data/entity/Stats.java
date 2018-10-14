package it.pathfinder.rollerbot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Stats {

    @Id
    private Long id;
    private Long pathfinderPg;
    private Long levelNumber;
    private Long stFortitude;
    private Long stReflex;
    private Long stWill;
    private Long lvlStrength;
    private Long lvlDexterity;
    private Long lvlConstitution;
    private Long lvlIntelligence;
    private Long lvlWisdom;
    private Long lvlCharisma;
    private Long lf;
    private String custom;

}
