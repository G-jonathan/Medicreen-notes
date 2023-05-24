package com.openclassroomsProject.Mediscreennotes.service.impl;

import com.openclassroomsProject.Mediscreennotes.model.Note;
import com.openclassroomsProject.Mediscreennotes.repository.NoteRepository;
import com.openclassroomsProject.Mediscreennotes.service.INoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Service
public class NoteServiceImpl implements INoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Get a note by its id.
     *
     * @param id The id of the note to get.
     * @return The note corresponding to the id.
     */
    @Override
    public Optional<Note> getNoteById(String id) {
        LOGGER.info("[SERVICE]-> call method : getNoteById [PARAM]-> id = " + id);
        return noteRepository.findById(id);
    }

    /**
     * Get all notes for a given patient.
     *
     * @param patientId Patient identifier.
     * @return The list of patient notes.
     */
    @Override
    public List<Note> getAllNotesByPatientId(Integer patientId) {
        LOGGER.info("[SERVICE]-> call method : getAllNotesByPatientId [PARAM]-> id = " + patientId);
        return noteRepository.findByPatientId(patientId);
    }

    /**
     * Get all notes from database.
     *
     * @return A list of notes.
     */
    @Override
    public List<Note> getAllNotes() {
        LOGGER.info("[SERVICE]-> call method : getAllNotes");
        return noteRepository.findAll();
    }

    /**
     * Add a new note.
     * Return an error if the resource already exist. To update a resource, @see updateNote().
     *
     * @param note The note to create.
     * @return The created note.
     */
    @Override
    public Note createNote(Note note) {
        LOGGER.info("[SERVICE]-> call method : addNote [PARAM]-> note = " + note);
        return noteRepository.insert(note);
    }

    /**
     * Update a note.
     *
     * @param note The note to update.
     * @return The updated note.
     */
    @Override
    public Note updateNote(Note note) {
        LOGGER.info("[SERVICE]-> call method : updateNote [PARAM]-> note = " + note);
        return noteRepository.save(note);
    }

    /**
     * Delete a note from its id.
     *
     * @param id The id of the note to delete.
     */
    @Override
    public void deleteNoteById(String id) {
        LOGGER.info("[SERVICE]-> call method : deleteNoteById [PARAM]-> id = " + id);
        noteRepository.deleteById(id);
    }
}