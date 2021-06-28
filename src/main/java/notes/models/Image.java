package notes.models;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Image extends BaseEntity {

    private String description;

    // List of Size objects
    // E.g.
    // Original size
    // Thumbnail size
    // Fullscreen size
    // Note size
    // ==============
    // String fileId; // to generate the URL to raw data
    // Long size;
    // Integer width;
    // Integer height;

}
