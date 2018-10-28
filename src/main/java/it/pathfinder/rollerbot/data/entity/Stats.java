package it.pathfinder.rollerbot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pathfinder_pg_id")
    private PathfinderPg pathfinderPg;

    private Integer hp = 0;

    @Column(name = "as_strength")
    private Integer asStrength = 0;

    @Column(name = "as_dexterity")
    private Integer asDextery = 0;

    @Column(name = "as_constitution")
    private Integer asConstitution = 0;

    @Column(name = "as_charisma")
    private Integer asCharisma = 0;

    @Column(name = "as_intelligence")
    private Integer asIntelligence = 0;

    @Column(name = "as_wisdom")
    private Integer asWisdom = 0;

    @Column(name = "ts_fortitude")
    private Integer tsFortitude = 0;

    @Column(name = "ts_will")
    private Integer tsWill = 0;

    @Column(name = "ts_reflex")
    private Integer tsReflex = 0;

    @Column(name = "base_attack_bonus")
    private Integer baseAttackBonus = 0;

    @Column(name = "level")
    private Integer level = 0;

    @Column(name = "init")
    private Integer init = 0;

    @Column(name = "armor_class")
    private Integer armorClass = 0;

    @Column(name = "surprise_armor_class")
    private Integer surpriseArmorClass = 0;

    @Column(name = "contact_armor_class")
    private Integer contactArmorClass = 0;

    public Stats(PathfinderPg pathfinderPg) {
        this.pathfinderPg = pathfinderPg;
    }

    public void init() {
        hp = 0;
        asWisdom = 0;
        asIntelligence = 0;
        asCharisma = 0;
        asConstitution = 0;
        asDextery = 0;
        asStrength = 0;
        tsReflex = 0;
        tsFortitude = 0;
        tsWill = 0;
        init = 0;
        level = 0;
        armorClass = 0;
        surpriseArmorClass = 0;
        contactArmorClass = 0;
    }

}
