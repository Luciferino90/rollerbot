package dto.generic.entity;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.PathfinderPg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force=true)
public class PathfinderPgDetail extends GenericDTO implements Serializable {

    private PathfinderPg pathfinderPg;

    private String className;

    public PathfinderPgDetail(PathfinderPg pathfinderPg)
    {
        this.pathfinderPg = pathfinderPg;
    }

}
