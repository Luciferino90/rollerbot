package dto.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class Error extends GenericDTO implements Serializable {

    private String className;
    private String errorDetail;

    public Error(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    @Override
    public String toString() {
        return errorDetail;
    }

}
