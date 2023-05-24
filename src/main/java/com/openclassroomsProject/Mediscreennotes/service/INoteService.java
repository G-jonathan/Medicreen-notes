package com.openclassroomsProject.Mediscreennotes.service;

import com.openclassroomsProject.Mediscreennotes.model.Note;
import java.util.List;
import java.util.Optional;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public interface INoteService {

    Optional<Note> getNoteById(String id);

    List<Note> getAllNotesByPatientId(Integer id);

    List<Note> getAllNotes();

    Note createNote(Note note);

    Note updateNote(Note note);

    void deleteNoteById(String id);
}