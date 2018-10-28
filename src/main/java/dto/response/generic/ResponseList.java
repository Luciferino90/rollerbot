package dto.response.generic;

import dto.generic.DataDTO;
import dto.generic.GenericDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseList extends DataDTO implements Serializable {

    private List<GenericDTO> detailList;

    public ResponseList() {
        detailList = new ArrayList<>();
    }

    public ResponseList(List<GenericDTO> genericList) {
        detailList = new ArrayList<>(genericList);
    }

    @Override
    public String toString() {
        if (detailList.isEmpty())
            return "No record set";
        return detailList.stream().map(GenericDTO::toString).collect(Collectors.joining("\n"));
    }

}
