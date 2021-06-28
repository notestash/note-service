package notes.converters;

import notes.dto.ImageDto;
import notes.models.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter extends BaseEntityDtoConverter<Image, ImageDto, ImageDto> {

    public ImageConverter() {
        super(Image.class);
    }

    @Override
    public void enrichEntityWithDto(Image entity, ImageDto saveDto) {
        entity.setDescription(saveDto.getDescription());
    }

    @Override
    public ImageDto convertEntityToDto(Image entity) {
        return ImageDto.builder()
                .id(entity.getId())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .description(entity.getDescription())
                .build();
    }

}
