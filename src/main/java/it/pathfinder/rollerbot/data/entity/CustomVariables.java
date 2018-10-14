package it.pathfinder.rollerbot.data.entity;

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
public class CustomVariables {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pathfinder_pg_id")
    private PathfinderPg pathfinderPg;

    @ManyToOne
    @JoinColumn(name = "telegram_user_id")
    private TelegramUser telegramUser;

    private String customName;
    private String customValue;

}
