/**
 *
 */
package dto.generic;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dto.response.generic.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonSubTypes({ @JsonSubTypes.Type(value = GenericResponse.class) })
public class DataDTO extends GenericDTO implements Serializable
{

    private static final long serialVersionUID = 20180515_1202L;

}
