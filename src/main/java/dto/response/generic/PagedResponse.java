package dto.response.generic;

import dto.generic.DataDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class PagedResponse extends DataDTO implements Serializable {

    private static final long serialVersionUID = -2050140538526591952L;
    long totalElements;
    long totalPages;
    long number;
    long size;

    private String className;

    public boolean isFirst() {
        return isEmpty() || this.getNumber() == 0;
    }

    public boolean isEmpty() {
        return this.getTotalElements() == 0;
    }

    public boolean isLast() {
        return isEmpty() || this.getNumber() == (this.getTotalPages() - 1);
    }

}
