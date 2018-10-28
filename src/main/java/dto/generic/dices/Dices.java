package dto.generic.dices;

import dto.generic.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class Dices extends GenericDTO implements Serializable {

    private String username;

    private String className;

    private List<SingleDiceResponse> singleDiceResponseList = new ArrayList<>();

    public Dices(String username) {
        this.username = username;
    }

    public void addSingleDiceResponse(SingleDiceResponse singleDiceResponse) {
        singleDiceResponseList.add(singleDiceResponse);
    }

    @Override
    public String toString() {
        return singleDiceResponseList.stream().map(singleDiceResponse -> {
            singleDiceResponse.setUsername(username);
            return singleDiceResponse.toString();
        }).collect(Collectors.joining("\n"));
    }

}
