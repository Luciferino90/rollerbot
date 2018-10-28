package dto.generic.entity;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class DefaultDetail extends GenericDTO implements Serializable {

    private Default aDefault;

    @Override
    public String toString() {
        return String.format("%s => %s", aDefault.getName(), aDefault.getCommand());
    }


}
