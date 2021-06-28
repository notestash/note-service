package notes.services;

import lombok.SneakyThrows;
import notes.models.Note;
import notes.repositories.NoteDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteDao noteDao;

    private NoteService tested;

    @BeforeEach
    public void setup() {
        tested = new NoteService(noteDao);
    }

    @Test
    @SneakyThrows
    public void getNote_returnsNote() {
        long noteId = 1;
        Note expected = new Note();

        when(noteDao.findById(noteId)).thenReturn(Optional.of(expected));

        Note actual = tested.getNote(noteId);

        assertThat(actual).isEqualTo(expected);

        verify(noteDao).findById(noteId);
        verifyNoMoreInteractions(noteDao);
    }

}
