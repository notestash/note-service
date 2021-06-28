package notes.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(builderMethodName = "baseBuilder")
public class BaseDto implements Dto {

    private Long id;
    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;

}
