package dto.generic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dto.response.generic.GenericResponse;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonSubTypes({@JsonSubTypes.Type(value = GenericResponse.class)})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GenericDTO implements Serializable {
    private static final long serialVersionUID = 20180515_1203L;

    @Override
    public abstract String toString();

}
