package notes.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import notes.converters.NoteConverter;
import notes.dto.NoteGetDto;
import notes.dto.NoteSaveDto;
import notes.exceptions.NoteNotFoundException;
import notes.models.Note;
import notes.services.NoteService;
import notes.utils.security.NoteSecurity;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final NoteConverter noteConverter;

    private static final String CLAIM_UID = "uid";

    @PostMapping("/notes/")
    @PreAuthorize("hasAuthority('SCOPE_notes:create')")
    public NoteGetDto postNote(
            @RequestBody @NonNull @Valid NoteSaveDto noteSaveDto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Note note = noteConverter.newEntityFromDto(noteSaveDto, jwt.getClaimAsString(CLAIM_UID));
        Note savedNote = noteService.addNote(note);
        return noteConverter.convertEntityToDto(savedNote);
    }

    @GetMapping("/notes/{id}")
    @PreAuthorize("hasAuthority('SCOPE_notes:read')")
    public ResponseEntity<NoteGetDto> getNote(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            Note note = noteService.getNote(id);
            NoteSecurity.isAuthorizedToAccessUserNotes(note.getCreatedBy(), jwt);

            NoteGetDto noteGetDto = noteConverter.convertEntityToDto(note);
            return new ResponseEntity<>(noteGetDto, OK);
        } catch (NoteNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }

    @GetMapping("/notes/")
    @PreAuthorize("hasAuthority('SCOPE_notes:read')")
    public List<NoteGetDto> getAllNotes(
            Pageable pageable,
            @AuthenticationPrincipal Jwt jwt
    ) {
        NoteSecurity.isAuthorizedToAccessAllNotes(jwt);

        List<Note> noteList = noteService.getNotes(pageable);
        return noteConverter.convertEntityToDto(noteList);
    }

    @GetMapping("/users/{userId}/notes/")
    @PreAuthorize("hasAuthority('SCOPE_notes:read')")
    public List<NoteGetDto> getUserNotes(
            @PathVariable("userId") String userId,
            Pageable pageable,
            @AuthenticationPrincipal Jwt jwt
    ) {
        NoteSecurity.isAuthorizedToAccessUserNotes(userId, jwt);

        List<Note> noteList = noteService.getNotes(userId, pageable);
        return noteConverter.convertEntityToDto(noteList);
    }

    @PutMapping("/notes/{id}")
    @PreAuthorize("hasAuthority('SCOPE_notes:update')")
    public ResponseEntity<NoteGetDto> putNote(
            @PathVariable("id") Long id,
            @RequestBody @NonNull @Valid NoteSaveDto noteSaveDto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            noteSaveDto.setId(id);
            Note note = noteService.getNote(id);
            NoteSecurity.isAuthorizedToAccessUserNotes(note.getCreatedBy(), jwt);

            noteConverter.enrichEntityWithDto(note, noteSaveDto);
            Note updatedNote = noteService.updateNote(note);
            NoteGetDto updatedNoteGetDto = noteConverter.convertEntityToDto(updatedNote);
            return new ResponseEntity<>(updatedNoteGetDto, OK);
        } catch (NoteNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }

    @DeleteMapping("/notes/{id}")
    @PreAuthorize("hasAuthority('SCOPE_notes:delete')")
    public ResponseEntity<String> deleteNote(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            Note note = noteService.getNote(id);
            NoteSecurity.isAuthorizedToAccessUserNotes(note.getCreatedBy(), jwt);

            noteService.deleteNote(id);
            return new ResponseEntity<>(OK);
        } catch (NoteNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}