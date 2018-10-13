package it.pathfinder.rollerbot.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Levels {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pathfinder_pg_id")
    private PathfinderPg pathfinderPg;

    private Long levelNumber;
    private Long stFortitude;
    private Long stWill;
    private Long stReflex;
    private Long lvlStrength;
    private Long lvlDexterity;
    private Long lvlConstitution;
    private Long lvlIntelligence;
    private Long lvlWisdom;
    private Long lvlCharisma;
    private Long lf;
    private String custom;

}
