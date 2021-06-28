package notes.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Note extends BaseEntity {

    private String message;

    @ManyToMany
    private List<Image> images;

}
