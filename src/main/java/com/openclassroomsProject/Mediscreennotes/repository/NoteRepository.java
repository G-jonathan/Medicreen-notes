package com.openclassroomsProject.Mediscreennotes.repository;

import com.openclassroomsProject.Mediscreennotes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface NoteRepository extends MongoRepository <Note, String> {

    List<Note> findByPatientId(Integer patientId);
}