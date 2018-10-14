package dto.generic.dices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dto.generic.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Dices extends GenericDTO implements Serializable {

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
