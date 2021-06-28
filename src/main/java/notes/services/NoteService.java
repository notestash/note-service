package notes.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.exceptions.NoteNotFoundException;
import notes.models.Note;
import notes.repositories.NoteDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteDao noteDao;

    public Note getNote(@NonNull Long id) throws NoteNotFoundException {
        log.debug("Getting Note with ID [{}]", id);
        Optional<Note> noteOptional = noteDao.findById(id);
        if (noteOptional.isPresent()) {
            log.info("Found Note with ID [{}]", id);
            return noteOptional.get();
        } else {
            log.info("Could not find Note with ID [{}]", id);
            throw new NoteNotFoundException();
        }
    }

    // Get all public notes
    public List<Note> getNotes(@NonNull Pageable pageable) {
        log.debug("Getting Notes");
        List<Note> noteList;
        Page<Note> page = noteDao.findAll(pageable);
        if (page.hasContent()) {
            noteList = page.getContent();
            log.info("Found {} Notes", noteList.size());
            return noteList;
        } else {
            log.info("Notes not found");
            return List.of();
        }
    }

    public List<Note> getNotes(@NonNull String userId, @NonNull Pageable pageable) {
        log.debug("Getting Notes");
        List<Note> noteList;
        Page<Note> page = noteDao.findByCreatedBy(userId, pageable);
        if (page.hasContent()) {
            noteList = page.getContent();
            log.info("Found {} Notes", noteList.size());
            return noteList;
        }
        log.info("Notes not found");
        return List.of();
    }

    public Note addNote(@NonNull Note note) {
        log.debug("Adding new Note");
        log.debug(note.toString());
        if (note.getId() != null) {
            throw new IllegalArgumentException("Note ID not null while creating a new Note");
        }
        Note savedNote = noteDao.save(note);
        log.info("Added Note with ID [{}]", savedNote.getId());
        return savedNote;
    }

    public Note updateNote(@NonNull Note note) {
        log.debug(note.toString());
        if (note.getId() == null) {
            throw new IllegalArgumentException("Note ID is null while updating an existing Note");
        }
        Long id = note.getId();
        log.debug("Updating Note with ID [{}]", id);
        Note updatedNote = noteDao.save(note);
        log.info("Updated Note with ID [{}]", id);
        return updatedNote;
    }

    public void deleteNote(@NonNull Long id) throws NoteNotFoundException {
        log.debug("Deleting Note with ID [{}]", id);
        try {
            noteDao.deleteById(id);
            log.info("Deleted Note with ID [{}]", id);
        } catch (EmptyResultDataAccessException ex) {
            log.info("Could not find to delete Note with ID [{}]", id);
            throw new NoteNotFoundException();
        }
    }

}
