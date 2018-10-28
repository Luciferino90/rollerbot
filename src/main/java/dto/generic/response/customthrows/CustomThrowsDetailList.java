package dto.generic.response.customthrows;

import dto.generic.GenericDTO;
import dto.generic.entity.CustomDetail;
import it.pathfinder.rollerbot.data.entity.Custom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomThrowsDetailList extends GenericDTO implements Serializable {

    private List<CustomDetail> customDetailList;

    public CustomThrowsDetailList() {
        customDetailList = new ArrayList<>();
    }

    public void convertCustomThrowsList(List<Custom> customThrows) {
        customDetailList = customThrows.stream().map(CustomDetail::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        if (customDetailList.isEmpty())
            return "No record set";
        return customDetailList.stream().map(CustomDetail::toString).collect(Collectors.joining("\n"));
    }

}
