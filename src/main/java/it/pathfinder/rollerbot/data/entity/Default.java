package it.pathfinder.rollerbot.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "default_vars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Default implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String command;

    @Override
    public String toString() {
        return String.format("%s: %s", name, command);
    }

}
