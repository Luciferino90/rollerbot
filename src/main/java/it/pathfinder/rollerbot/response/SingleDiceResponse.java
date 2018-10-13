package it.pathfinder.rollerbot.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleDiceResponse implements Serializable {

    String username;

    String partialResult;

    String result;

    public SingleDiceResponse(String username)
    {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return String.format("@%s: [%s] = %s", username, partialResult, result);
    }

}
