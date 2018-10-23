package dto.request.customthrows;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomThrowsRequest {

    private Long tgOid;
    private String customName;
    private String customFormula;

}
