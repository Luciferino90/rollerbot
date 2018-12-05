package it.pathfinder.rollerbot.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PathfinderPg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "telegram_user_id")
    private TelegramUser telegramUser;

    @Override
    public String toString() {
        return String.format("%s: %s", id, name);
    }


}
