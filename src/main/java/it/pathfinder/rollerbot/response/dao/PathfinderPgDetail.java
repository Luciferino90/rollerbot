package it.pathfinder.rollerbot.response.dao;

import it.pathfinder.rollerbot.dao.entity.PathfinderPg;
import it.pathfinder.rollerbot.response.Info;
import lombok.Data;

import java.io.Serializable;

@Data
public class PathfinderPgDetail extends PathfinderPg implements Serializable, Info {

    public PathfinderPgDetail()
    {
        super();
    }

    public PathfinderPgDetail(PathfinderPg pathfinderPg)
    {
        super(pathfinderPg);
    }

}
