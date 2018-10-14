package dto.generic.dices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleDiceResponse implements Serializable {

    String partialResult;

    String result;

}
