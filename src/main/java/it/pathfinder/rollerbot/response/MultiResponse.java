package it.pathfinder.rollerbot.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MultiResponse implements Serializable {

    @JsonIgnore
    private String username;

    List<SingleDiceResponse> singleDiceResponseList = new ArrayList<>();

    public MultiResponse(String username) {
        this.username = username;
    }

    public void addSingleDiceResponse(SingleDiceResponse singleDiceResponse)
    {
        singleDiceResponseList.add(singleDiceResponse);
    }

    @Override
    public String toString()
    {
        return singleDiceResponseList
                .stream()
                .map(SingleDiceResponse::toString)
                .collect(Collectors.joining("\n"));
    }

}
