package dto.response.generic;

import dto.generic.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse extends GenericDTO implements Serializable
{

    private static final long serialVersionUID = 20180515_1207L;

    private GenericDTO data;

}
