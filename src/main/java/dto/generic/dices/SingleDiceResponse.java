package dto.generic.dices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class SingleDiceResponse implements Serializable {

    private String result;
    private String partialResult;
    private String username;

    @Override
    public String toString() {
        return String.format("%s => [%s]: %s", username, partialResult, result);
    }

}
