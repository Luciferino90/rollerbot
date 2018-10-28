package it.pathfinder.rollerbot.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Custom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pathfinder_pg_id")
    private PathfinderPg pathfinderPg;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "telegram_user_id")
    private TelegramUser telegramUser;

    @Column(name = "custom_name")
    private String customName;

    @Column(name = "custom_command")
    private String customValue;

    @Override
    public String toString() {
        return String.format("%s: %s", customName, customValue);
    }

}
