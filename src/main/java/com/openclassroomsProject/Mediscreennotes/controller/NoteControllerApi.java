package com.openclassroomsProject.Mediscreennotes.controller;

import com.openclassroomsProject.Mediscreennotes.model.Note;
import com.openclassroomsProject.Mediscreennotes.service.INoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@RestController
@RequestMapping("/api/note")
public class NoteControllerApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteControllerApi.class);

    @Autowired
    private INoteService noteService;

    /**
     * Get the list of all notes.
     *
     * @return Contains a list of notes if they exist, otherwise returns a 404 not found error.
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        LOGGER.info("[CONTROLLER API]-> call method : getAllNotes");
        List<Note> noteList = noteService.getAllNotes();
        if (!noteList.isEmpty()) {
            return ResponseEntity.ok(noteList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * GET Request, get all notes for a patient.
     *
     * @param patientId Patient identifier.
     * @return Contains a list of all patient notes if they exist, otherwise returns a 404 not found error.
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable Integer patientId) {
        LOGGER.info("[CONTROLLER API]-> call method : getNotesByPatientId [PARAM]-> patientId = " + patientId);
        List<Note> notesList = noteService.getAllNotesByPatientId(patientId);
        if (!notesList.isEmpty()) {
            return ResponseEntity.ok(notesList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * GET Request, get note by id.
     *
     * @param noteId Identifier of the wanted note.
     * @return Contains the note if it exists, otherwise returns a 404 not found error.
     */
    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable String noteId) {
        LOGGER.info("[CONTROLLER API]-> call method : getNoteById [PARAM]-> noteId = " + noteId);
        Optional<Note> note = noteService.getNoteById(noteId);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new note.
     *
     * @param note the note to create.
     * @return ResponseEntity containing the note created with status code 201, Created.
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        LOGGER.info("[CONTROLLER API]-> call method : createNote [PARAM]-> note = " + note);
        Note createdNote = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    /**
     * Updates an existing note.
     *
     * @param noteId The ID of the note to update.
     * @param note   The new version of the note.
     * @return ResponseEntity containing the updated note if it exists, otherwise a 404, not found response.
     */
    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable String noteId, @RequestBody Note note) {
        LOGGER.info("[CONTROLLER API]-> call method : updateNote [PARAM]-> noteId = " + noteId + " [PARAM]-> note= " + note);
        Optional<Note> existingNote = noteService.getNoteById(noteId);
        if (existingNote.isPresent()) {
            existingNote.get().setComment(note.getComment());
            return ResponseEntity.ok(noteService.updateNote(existingNote.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes an existing note.
     *
     * @param noteId The identifier of the note to delete.
     * @return ResponseEntity with no content if the note is deleted successfully, or a 404, not found response if the note does not exist.
     */
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable String noteId) {
        LOGGER.info("[CONTROLLER API]-> call method : deleteNote [PARAM]-> noteId = " + noteId);
        Optional<Note> existingNote = noteService.getNoteById(noteId);
        if (existingNote.isPresent()) {
            noteService.deleteNoteById(existingNote.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}