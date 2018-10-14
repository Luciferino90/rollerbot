package dto.generic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GenericDTO implements Serializable
{
    private static final long serialVersionUID = 20180515_1203L;

    @Override
    public abstract String toString();

}
