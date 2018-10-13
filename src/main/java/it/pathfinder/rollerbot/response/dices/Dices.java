package it.pathfinder.rollerbot.response.dices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.pathfinder.rollerbot.response.Info;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Dices implements Serializable, Info {

    @JsonIgnore
    private String username;

    private List<SingleDiceResponse> singleDiceResponseList = new ArrayList<>();

    public Dices(String username) {
        this.username = username;
    }

    public void addSingleDiceResponse(SingleDiceResponse singleDiceResponse)
    {
        singleDiceResponseList.add(singleDiceResponse);
    }

}
