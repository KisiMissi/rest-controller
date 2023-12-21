package org.kaoden.ws.homework.controller.entry;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kaoden.ws.homework.controller.entry.dto.CreateEntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.EntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.SearchEntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.UpdateEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DBUnitExtension.class)
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

    @Test
    @ExpectedDataSet("entry\\CREATE_EXPECTED.json")
    void createEntryInRep() {
        // Arrange
        CreateEntryDTO entryToCreate = CreateEntryDTO.builder()
                                                     .name("test")
                                                     .description("test-description")
                                                     .links(List.of("test-link"))
                                                     .build();

        // Act
        client.post()
              .uri("/{url}/create", URL)
              .bodyValue(entryToCreate)
              .exchange()
              .expectStatus()
              .isCreated();
    }

    @Test
    @DataSet("entry\\GET_ALL.json")
    void getAllEntries() {
        // Arrange
        SearchEntryDTO searchDto = SearchEntryDTO.builder()
                                                 .build();
        EntryDTO expectedDto1 = EntryDTO.builder()
                                        .id(1L)
                                        .name("test-1")
                                        .description("test-description")
                                        .links(List.of("test-link"))
                                        .build();
        EntryDTO expectedDto2 = EntryDTO.builder()
                                        .id(2L)
                                        .name("test-2")
                                        .description("test-description")
                                        .links(List.of("test-link"))
                                        .build();

        // Act
        List<EntryDTO> result = client.method(HttpMethod.GET)
                                      .uri("/{url}/all", URL)
                                      .bodyValue(searchDto)
                                      .exchange()
                                      .expectStatus()
                                      .isOk()
                                      .expectBodyList(EntryDTO.class)
                                      .returnResult()
                                      .getResponseBody();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).contains(expectedDto1);
        assertThat(result).contains(expectedDto2);
    }

    @Test
    @DataSet("entry\\SEARCH_BY_NAME.json")
    void searchByNameShouldReturnTwoEntries() {
        // Arrange
        String name1 = "test-2";
        String name2 = "tEsT-2";
        SearchEntryDTO searchDto = SearchEntryDTO.builder()
                                                 .name(name2)
                                                 .build();
        EntryDTO expectedDto1 = EntryDTO.builder()
                                        .id(2L)
                                        .name(name1)
                                        .description("test-description")
                                        .links(List.of("test-link"))
                                        .build();
        EntryDTO expectedDto2 = EntryDTO.builder()
                                        .id(3L)
                                        .name(name2)
                                        .description("test-description")
                                        .links(List.of("test-link"))
                                        .build();

        // Act
        List<EntryDTO> result = client.method(HttpMethod.GET)
                                      .uri("/{url}/all", URL)
                                      .bodyValue(searchDto)
                                      .exchange()
                                      .expectBodyList(EntryDTO.class)
                                      .returnResult()
                                      .getResponseBody();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).contains(expectedDto1);
        assertThat(result).contains(expectedDto2);
    }

    @Test
    @DataSet("entry\\SEARCH_BY_DESCRIPTION.json")
    void searchByDescriptionShouldReturnTwoEntries() {
        // Arrange
        String description = "test-description-one";
        String description2 = "TeSt-DeScRipTioN-ONE";
        SearchEntryDTO searchDto = SearchEntryDTO.builder()
                                                 .description(description)
                                                 .build();
        EntryDTO expectedDto1 = EntryDTO.builder()
                                        .id(1L)
                                        .name("test-1")
                                        .description(description)
                                        .links(List.of("test-link"))
                                        .build();
        EntryDTO expectedDto2 = EntryDTO.builder()
                                        .id(2L)
                                        .name("test-2")
                                        .description(description2)
                                        .links(List.of("test-link"))
                                        .build();

        // Act
        List<EntryDTO> result = client.method(HttpMethod.GET)
                                      .uri("/{url}/all", URL)
                                      .bodyValue(searchDto)
                                      .exchange()
                                      .expectBodyList(EntryDTO.class)
                                      .returnResult()
                                      .getResponseBody();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).contains(expectedDto1);
        assertThat(result).contains(expectedDto2);
    }

    @Test
    @DataSet("entry\\SEARCH_BY_ID.json")
    void searchEntryById() {
        // Arrange
        Long id = 1L;
        EntryDTO expectedDto = EntryDTO.builder()
                                       .id(id)
                                       .name("test-1")
                                       .description("test-description")
                                       .links(List.of("test-link"))
                                       .build();

        // Act
        EntryDTO result = client.get()
                                .uri("/{url}/{id}", URL, id)
                                .exchange()
                                .expectBody(EntryDTO.class)
                                .returnResult()
                                .getResponseBody();

        // Assert
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    @DataSet("entry\\UPDATE.json")
    @ExpectedDataSet("entry\\UPDATE_EXPECTED.json")
    void updateEntry() {
        // Arrange
        Long id = 1L;
        UpdateEntryDTO updateEntryDTO = UpdateEntryDTO.builder()
                                                      .name("update")
                                                      .description("update-description")
                                                      .links(List.of("update-link"))
                                                      .build();

        // Act
        client.post()
              .uri("/{url}/{id}/update", URL, id)
              .bodyValue(updateEntryDTO)
              .exchange()
              .expectStatus()
              .isOk();
    }

    @Test
    @DataSet("entry\\DELETE.json")
    @ExpectedDataSet("entry\\DELETE_EXPECTED.json")
    void deleteEntry() {
        // Arrange
        Long id = 1L;

        // Assert
        client.post()
              .uri("/{url}/{id}/delete", URL, id)
              .exchange()
              .expectStatus()
              .isOk();
    }
}