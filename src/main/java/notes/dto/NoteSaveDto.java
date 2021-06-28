package notes.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class NoteSaveDto implements Dto {

    private Long id;
    private String message;
    private List<Long> imageIds;

}
