package dto.generic.entity;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.CustomThrows;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class CustomThrowsDetail extends GenericDTO implements Serializable {

    private CustomThrows customThrows;

    @Override
    public String toString()
    {
        return String.format("%s => %s", customThrows.getCustomName(), customThrows.getCustomCommand());
    }

}
