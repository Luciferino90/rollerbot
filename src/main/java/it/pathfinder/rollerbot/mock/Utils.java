package it.pathfinder.rollerbot.mock;

import it.pathfinder.rollerbot.dao.entity.PathfinderPg;

public class Utils {

    private Utils(){}


    public static PathfinderPg mockedCreateUser(String characterName)
    {
        PathfinderPg pathfinderPg = new PathfinderPg();
        pathfinderPg.setId(1L);
        pathfinderPg.setName(characterName);
        return pathfinderPg;
    }

}
