package notes.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ImageDto extends BaseDto {

    String description;

    @Builder
    public ImageDto(Long id, Date createdDate, Date lastModifiedDate, String createdBy, String description) {
        super(id, createdDate, lastModifiedDate, createdBy);
        this.description = description;
    }
}
