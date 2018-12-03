package it.pathfinder.rollerbot.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dto.generic.GenericDTO;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class TelegramUser extends GenericDTO implements Serializable {

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
    @ToStringExclude
    @HashCodeExclude
    @JoinColumn(name = "default_pathfinder_id")
    private PathfinderPg defaultPathfinderPg;

    @OneToMany(fetch = FetchType.LAZY)
    @ToStringExclude
    @HashCodeExclude
    private List<PathfinderPg> pathfinderPgList;

}
