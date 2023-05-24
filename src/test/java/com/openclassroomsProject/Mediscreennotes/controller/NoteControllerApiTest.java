package com.openclassroomsProject.Mediscreennotes.controller;

import com.openclassroomsProject.Mediscreennotes.model.Note;
import com.openclassroomsProject.Mediscreennotes.service.INoteService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.List;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test class for the NoteControllerApi controller.
 * * @author jonathan GOUVEIA
 * * @version 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoteControllerApiTest {
    private static final String LOCALHOST = "http://localhost:";
    private static final String URI = "/api/note";
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteControllerApiTest.class);

    @Autowired
    private INoteService noteService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Container
    private static final MongoDBContainer MONGODB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    /**
     * Allows to sets dynamic properties for MongoDB data source.
     *
     * @param registry The dynamic property registry.
     */
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGODB_CONTAINER::getReplicaSetUrl);
    }

    /**
     * Method executed before all tests.
     * Starts the MongoDB container.
     */
    @BeforeAll
    static void setup() {
        MONGODB_CONTAINER.start();
    }

    /**
     * Method executed after all tests.
     * Stop the MongoDB container.
     */
    @AfterAll
    static void tearDown() {
        MONGODB_CONTAINER.stop();
    }

    /**
     * Method executed before each test.
     * Clean database.
     */
    @BeforeEach
    public void clearDatabase() {
        mongoTemplate.getDb().drop();
    }

    /**
     * Returns the base URL for requests.
     *
     * @return Base URL for requests.
     */
    private String getBaseUrl() {
        return LOCALHOST + serverPort + URI;
    }

    /**
     * Tests if the MongoDB container is running.
     */
    @Test
    @DisplayName("Container is running")
    @Order(1)
    void test() {
        assertThat(MONGODB_CONTAINER.isRunning()).isTrue();
    }

    @Test
    @DisplayName("Get all notes when note exist")
    @Order(2)
    void getAllNotes_whenThereAreNotes_thenReturnTheCorrectList() {
        Note note1 = new Note("1", 1, "test");
        noteService.createNote(note1);
        Note note2 = new Note("2", 2, "test");
        noteService.createNote(note2);
        ResponseEntity<List<Note>> responseEntity =
                restTemplate.exchange(
                        getBaseUrl(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Note>>() {
                        }
                );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("Get all notes when notes does not exist")
    @Order(3)
    void getAllNotes_whenThereIsNoNotes_thenReturnNoContent() {
        ResponseEntity<List<Note>> responseEntity =
                restTemplate.exchange(
                        getBaseUrl(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Note>>() {
                        }
                );
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("Get notes by patient id when notes exist")
    @Order(4)
    void getNotesByPatientId_whenNotesExist_thenReturnCorrectList() {
        Note note1 = new Note("3", 3, "test");
        noteService.createNote(note1);
        Note note2 = new Note("4", 3, "test");
        noteService.createNote(note2);
        Note note3 = new Note("5", 3, "test");
        noteService.createNote(note3);
        ResponseEntity<List<Note>> responseEntity =
                restTemplate.exchange(
                        getBaseUrl() + "/patient/3",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Note>>() {
                        }
                );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(3, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("Get notes by patient id when notes does not exist")
    @Order(5)
    void getNotesByPatientId_whenThereIsNoNotes_thenReturnNoContent() {
        ResponseEntity<List<Note>> responseEntity =
                restTemplate.exchange(
                        getBaseUrl(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Note>>() {
                        }
                );
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("Get note by id when note exist")
    @Order(6)
    void getNoteById_whenNoteExist_thenReturnTheCorrectNote() {
        Note note = new Note("6", 4, "test");
        noteService.createNote(note);
        ResponseEntity<Note> responseEntity = restTemplate.getForEntity(getBaseUrl() + "/{noteId}", Note.class, note.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), "6");
    }

    @Test
    @DisplayName("Get note by id when note does not exist")
    @Order(7)
    void getNoteById_whenNoteDoesNotExist_thenReturnNotFound() {
        ResponseEntity<Note> responseEntity = restTemplate.getForEntity(getBaseUrl() + "/{noteId}", Note.class, "11");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("Create a new note")
    @Order(8)
    void createNote_thenReturnTheCreatedNote() {
        Note note = new Note("7", 5, "test");
        ResponseEntity<Note> responseEntity = restTemplate.postForEntity(getBaseUrl(), note, Note.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), "7");
        assertEquals(responseEntity.getBody().getPatientId(), 5);
        assertEquals(responseEntity.getBody().getComment(), "test");
    }

    @Test
    @DisplayName("Update an existing note")
    @Order(9)
    void updateNote_whenNoteExist_thenReturnTheUpdatedNote() {
        Note note = new Note("8", 6, "test");
        noteService.createNote(note);
        Note noteUpdated = new Note("8", 6, "Test Modified");
        ResponseEntity<Note> responseEntity = restTemplate.exchange(getBaseUrl() + "/8", HttpMethod.PUT, new HttpEntity<>(noteUpdated), Note.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Test Modified", Objects.requireNonNull(responseEntity.getBody()).getComment());
    }

    @Test
    @DisplayName("Trying to update a note that does not exist ")
    @Order(10)
    void updateNote_whenNoteDoesNotExist_thenReturnNotFound() {
        Note note = new Note("9", 7, "test");
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getBaseUrl() + "/11", HttpMethod.PUT, new HttpEntity<>(note), Void.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("Trying to delete an existing note")
    @Order(11)
    void deleteNote_whenNoteExist_thenReturnNoContent() {
        Note note = new Note("10", 8, "test");
        noteService.createNote(note);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getBaseUrl() + "/10", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("Trying to delete a note that does not exist ")
    @Order(12)
    void deleteNote_whenNoteDoesNotExist_thenReturnNotFound() {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getBaseUrl() + "/11", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}