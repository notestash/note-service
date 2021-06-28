package notes.converters;

import notes.dto.NoteGetDto;
import notes.dto.NoteSaveDto;
import notes.models.Image;
import notes.models.Note;
import notes.services.ImageService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NoteConverter extends BaseEntityDtoConverter<Note, NoteSaveDto, NoteGetDto> {

    private final ImageService imageService;

    public NoteConverter(ImageService imageService) {
        super(Note.class);
        this.imageService = imageService;
    }

    @Override
    public void enrichEntityWithDto(Note note, NoteSaveDto noteSaveDto) {
        note.setMessage(noteSaveDto.getMessage());
        note.setImages(noteSaveDto.getImageIds().stream().map(imageService::getImageById).collect(Collectors.toList()));
    }

    @Override
    public NoteGetDto convertEntityToDto(Note note) {
        return NoteGetDto.builder()
                .id(note.getId())
                .message(note.getMessage())
                .imageIds(note.getImages().stream().map(Image::getId).collect(Collectors.toList()))
                .createdDate(note.getCreatedDate())
                .createdBy(note.getCreatedBy())
                .lastModifiedDate(note.getLastModifiedDate())
                .build();
    }

}