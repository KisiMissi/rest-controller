package org.kaoden.ws.homework.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.controller.entry.dto.CreateEntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.EntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@AutoConfigureWebTestClient
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EntryControllerIT {

    static final String URL = "entries";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    WebTestClient client;
    @Autowired
    EntryRepository repository;

    @BeforeEach
    void setRepository() {
        repository.deleteAll();
    }

    private Entry getEntry(String name) {
        return Entry.builder()
                    .name(name)
                    .description("test-description")
                    .links(List.of("test-link"))
                    .build();
    }

    @Test
    void createEntryInRep() {
        // Arrange
        CreateEntryDTO entryToCreate = CreateEntryDTO.builder()
                                                     .name("test")
                                                     .description("test-description")
                                                     .links(List.of("test-link"))
                                                     .build();

        // Act
        EntityExchangeResult<EntryDTO> result = client.post()
                                                      .uri("/{url}/create", URL)
                                                      .bodyValue(entryToCreate)
                                                      .exchange()
                                                      .expectStatus()
                                                      .isCreated()
                                                      .expectBody(EntryDTO.class)
                                                      .returnResult();


        // Assert
        EntryDTO resultEntry = result.getResponseBody();
        assertThat(resultEntry.getName()).isEqualTo("test");
        assertThat(resultEntry.getDescription()).isEqualTo("test-description");
        assertThat(resultEntry.getLinks()).isEqualTo(List.of("test-link"));
    }

    @Test
    void getAllEntries() {
        // Arrange
        String name1 = "test-1";
        String name2 = "test-2";
        Entry entry1 = getEntry(name1);
        Entry entry2 = getEntry(name2);
        repository.save(entry1);
        repository.save(entry2);

        // Act
        EntityExchangeResult<List<EntryDTO>> result = client.get()
                                                            .uri("/{url}/all", URL)
                                                            .exchange()
                                                            .expectStatus()
                                                            .isOk()
                                                            .expectBodyList(EntryDTO.class)
                                                            .returnResult();

        // Assert
        assertThat(result.getResponseBody()).hasSize(2);
    }

    @Test
    void searchByNameShouldReturnTwoEntries() {
        // Arrange
        String name1 = "test-1";
        String name2 = "test-2";
        String name3 = "tEsT-2";
        Entry entry1 = getEntry(name1);
        Entry entry2 = getEntry(name2);
        Entry entry3 = getEntry(name3);
        repository.save(entry1);
        repository.save(entry2);
        repository.save(entry3);

        // Act
        EntityExchangeResult<List<EntryDTO>> result = client.get()
                                                            .uri(uriBuilder -> uriBuilder.path(URL)
                                                                                         .path("/all")
                                                                                         .queryParam("name", name2)
                                                                                         .build())
                                                            .exchange()
                                                            .expectBodyList(EntryDTO.class)
                                                            .returnResult();

        // Assert
        assertThat(result.getResponseBody()).hasSize(2);
    }

    @Test
    void searchByDescriptionShouldReturnTwoEntries() {
        // Arrange
        String description = "test-description-one";
        String description2 = "TeSt-DeScRipTioN-ONE";
        Entry entryToSave = Entry.builder()
                                 .name("test")
                                 .description(description)
                                 .links(List.of("test-link"))
                                 .build();
        Entry entryToSave2 = Entry.builder()
                                  .name("test-2")
                                  .description(description2)
                                  .links(List.of("test-link"))
                                  .build();
        repository.save(entryToSave);
        repository.save(entryToSave2);

        // Act
        EntityExchangeResult<List<EntryDTO>> result = client.get()
                                                            .uri(uriBuilder -> uriBuilder.path(URL)
                                                                               .path("/all")
                                                                               .queryParam("description", description)
                                                                               .build())
                                                            .exchange()
                                                            .expectBodyList(EntryDTO.class)
                                                            .returnResult();

        // Assert
        assertThat(result.getResponseBody()).hasSize(2);
    }

    @Test
    void searchEntryById() {
        // Arrange
        String name = "test";
        Entry entryToSave = getEntry(name);
        Entry savedEntry = repository.save(entryToSave);
        Long id = savedEntry.getId();

        // Act
        EntityExchangeResult<EntryDTO> result = client.get()
                                                      .uri("/{url}/{id}", URL, id)
                                                      .exchange()
                                                      .expectBody(EntryDTO.class)
                                                      .returnResult();

        // Assert
        assertThat(result.getResponseBody()
                         .getId()).isEqualTo(savedEntry.getId());
    }

    @Test
    void updateEntry() {
        // Arrange
        Entry entryToSave = Entry.builder()
                                 .name("test")
                                 .description("test-description")
                                 .links(List.of("test-link"))
                                 .build();
        Entry savedEntry = repository.save(entryToSave);
        Long id = savedEntry.getId();
        UpdateEntryDTO updateEntryDTO = UpdateEntryDTO.builder()
                                                      .name("test-update")
                                                      .description("update-description")
                                                      .links(List.of("update-link"))
                                                      .build();
        EntryDTO expectedEntry = EntryDTO.builder()
                                         .id(id)
                                         .name("test-update")
                                         .description("update-description")
                                         .links(List.of("update-link"))
                                         .build();

        // Act
        EntityExchangeResult<EntryDTO> result = client.post()
                                                      .uri("/{url}/{id}/update", URL, id)
                                                      .bodyValue(updateEntryDTO)
                                                      .exchange()
                                                      .expectStatus()
                                                      .isOk()
                                                      .expectBody(EntryDTO.class)
                                                      .returnResult();

        // Assert
        assertThat(result.getResponseBody()).isEqualTo(expectedEntry);
    }

    @Test
    void deleteEntry() {
        // Arrange
        Entry savedEntry = repository.save(getEntry("test"));
        Long id = savedEntry.getId();

        // Assert
        client.post()
              .uri("/{url}/{id}/delete", URL, id)
              .exchange()
              .expectStatus()
              .isOk();

        // Assert
        assertThat(repository.existsById(id)).isFalse();
    }
}