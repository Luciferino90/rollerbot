package it.pathfinder.rollerbot.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tgName;
    private String tgSurname;
    private String tgUsername;
    private Date registerDate;
    private Long tgId;
    private String email;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "default_pathfinder_id")
    private PathfinderPg defaultPathfinderPg;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PathfinderPg> pathfinderPgList;

}
