package com.openclassroomsProject.Mediscreennotes.service.impl;

import com.openclassroomsProject.Mediscreennotes.model.Note;
import com.openclassroomsProject.Mediscreennotes.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @Test
    @DisplayName("The resource exist")
    void getNoteById_whenNoteExist_thenReturnTheNote() {
        String noteId = "1";
        Integer patientId = 1;
        String comment = "Test comment";
        Note note = new Note("1", patientId, comment);
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));
        Optional<Note> result = noteService.getNoteById(noteId);
        assertTrue(result.isPresent());
        assertEquals(noteId, result.get().getId());
        assertEquals(comment, result.get().getComment());
        assertEquals(patientId, result.get().getPatientId());
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    @DisplayName("The resource does not exist")
    void getNoteById_whenNoteDoesNotExist_thenReturnAnEmptyOptional() {
        String noteId = "1";
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());
        Optional<Note> result = noteService.getNoteById(noteId);
        assertTrue(result.isEmpty());
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    @DisplayName("Resource list exist for this patient")
    void getAllNotesByPatientId_whenNotesExist_thenReturnTheCorrectList() {
        Integer patientId = 1;
        String note1Id = "1";
        String comment1 = "Comment number 1";
        Note note1 = new Note(note1Id, patientId, comment1);
        String note2Id = "2";
        String comment2 = "Comment number 2";
        Note note2 = new Note(note2Id, patientId, comment2);
        List<Note> notes = Arrays.asList(note1, note2);
        when(noteRepository.findByPatientId(patientId)).thenReturn(notes);
        List<Note> result = noteService.getAllNotesByPatientId(patientId);
        assertEquals(notes.size(), result.size());
        assertEquals(notes.get(0).getId(), result.get(0).getId());
        assertEquals(notes.get(1).getId(), result.get(1).getId());
        assertEquals(notes.get(0).getPatientId(), result.get(0).getPatientId());
        assertEquals(notes.get(1).getPatientId(), result.get(1).getPatientId());
        assertEquals(notes.get(0).getComment(), result.get(0).getComment());
        assertEquals(notes.get(1).getComment(), result.get(1).getComment());
        verify(noteRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    @DisplayName("No notes exist for this patient")
    void getAllNotesByPatientId_whenNotesDoesNotExist_thenReturnAnEmptyList() {
        Integer patientId = 1;
        when(noteRepository.findByPatientId(patientId)).thenReturn(new ArrayList<>());
        List<Note> result = noteService.getAllNotesByPatientId(patientId);
        assertTrue(result.isEmpty());
        verify(noteRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    @DisplayName("Get a list with random number of resources")
    void getAllNotes_whenNumberOfNotesIsRandom_thenReturnTheCorrectList() {
        Random random = new Random();
        int randomInt = random.nextInt(8) + 2;
        List<Note> notes = new ArrayList<>();
        for (int index1 = 0; index1 < randomInt; index1++) {
            Note note = new Note("noteId" + index1, index1, "comment" + index1);
            notes.add(note);
        }
        when(noteRepository.findAll()).thenReturn(notes);
        List<Note> result = noteService.getAllNotes();
        for (int index2 = 0; index2 < randomInt; index2++) {
            assertEquals(notes.get(index2).getId(), result.get(index2).getId());
            assertEquals(notes.get(index2).getPatientId(), result.get(index2).getPatientId());
            assertEquals(notes.get(index2).getComment(), result.get(index2).getComment());
        }
        assertEquals(notes.size(), result.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Create a new resource")
    void createNote_whenNoteExist_thenReturnTheCreatedNote() {
        Integer patientId = 1;
        String comment1 = "Comment test";
        Note note = new Note(patientId, comment1);
        when(noteRepository.insert(note)).thenReturn(note);
        Note result = noteService.createNote(note);
        assertEquals(note, result);
        verify(noteRepository, times(1)).insert(note);
    }

    @Test
    @DisplayName("Update an existing note")
    void updateNote_whenNoteAlreadyExist_thenReturnTheModifiedNote() {
        Note note = new Note();
        when(noteRepository.save(note)).thenReturn(note);
        Note result = noteService.updateNote(note);
        assertEquals(note, result);
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    @DisplayName("Delete an existing note")
    void deleteNoteById_whenNoteAlreadyExist_thenRepositoryIsCall() {
        String noteId = "1";
        noteService.deleteNoteById(noteId);
        verify(noteRepository, times(1)).deleteById(noteId);
    }
}