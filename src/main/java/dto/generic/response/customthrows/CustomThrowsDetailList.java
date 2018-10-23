package dto.generic.response.customthrows;

import dto.generic.GenericDTO;
import dto.generic.entity.CustomThrowsDetail;
import it.pathfinder.rollerbot.data.entity.CustomThrows;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomThrowsDetailList extends GenericDTO implements Serializable {

    private List<CustomThrowsDetail> customThrowsDetailList;

    public CustomThrowsDetailList()
    {
        customThrowsDetailList = new ArrayList<>();
    }

    public void convertCustomThrowsList(List<CustomThrows> customThrows)
    {
        customThrowsDetailList = customThrows.stream().map(CustomThrowsDetail::new).collect(Collectors.toList());
    }

    @Override
    public String toString()
    {
        if (customThrowsDetailList.isEmpty())
            return "No record set";
        return customThrowsDetailList.stream().map(CustomThrowsDetail::toString).collect(Collectors.joining("\n"));
    }

}
