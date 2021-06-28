package notes.dto;

import lombok.*;
import notes.models.Image;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class NoteGetDto extends BaseDto {

    private String message;
    private List<Long> imageIds;

    @Builder
    public NoteGetDto(Long id, Date createdDate, Date lastModifiedDate, String createdBy, String message, List<Long> imageIds) {
        super(id, createdDate, lastModifiedDate, createdBy);
        this.message = message;
        this.imageIds = imageIds;
    }
}
