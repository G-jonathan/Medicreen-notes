package com.openclassroomsProject.Mediscreennotes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Document(collection = "note")
public class Note {

    @Id
    private String id;
    private Integer patientId;
    private String comment;

    public Note() {
    }

    public Note(Integer patientId, String comment) {
        this.patientId = patientId;
        this.comment = comment;
    }

    public Note(String id, Integer patientId, String comment) {
        this.id = id;
        this.patientId = patientId;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", patientId=" + patientId +
                ", comment='" + comment + '\'' +
                '}';
    }
}