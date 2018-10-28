package dto.generic.entity;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.Custom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomDetail extends GenericDTO implements Serializable {

    private Custom custom;

    @Override
    public String toString() {
        return String.format("%s => %s", custom.getCustomName(), custom.getCustomValue());
    }

}
