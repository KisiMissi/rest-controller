package org.kaoden.ws.homework.api.assessment;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kaoden.ws.homework.api.assessment.dto.AssessmentDto;
import org.kaoden.ws.homework.api.assessment.dto.CreateAssessmentDto;
import org.kaoden.ws.homework.api.entry.dto.EntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DBUnitExtension.class)
@Testcontainers
@AutoConfigureWebTestClient
@FieldDefaults(level = PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssessmentControllerIT {

    static final String URL = "assessment";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    WebTestClient client;

    @Test
    @DataSet("assessment\\CREATE.json")
    @ExpectedDataSet("assessment\\CREATE_EXPECTED.json")
    void create() {
        // Arrange
        CreateAssessmentDto testAssessment = CreateAssessmentDto.builder()
                                                                .entryId(1L)
                                                                .value(1)
                                                                .comment("test-comment")
                                                                .build();

        // Act
        client.post()
              .uri("{URL}/create", URL)
              .bodyValue(testAssessment)
              .exchange()
              .expectStatus()
              .isCreated();
    }

    @Test
    @DataSet("assessment\\GET_ALL.json")
    void getAll() {
        // Arrange
        Long entryId = 1L;
        EntryDTO entryDTO = EntryDTO.builder()
                                    .id(entryId)
                                    .name("test")
                                    .description("test-description")
                                    .links(List.of("test-link"))
                                    .build();
        AssessmentDto expectedAssessment1 = AssessmentDto.builder()
                                                         .id(1L)
                                                         .entryDto(entryDTO)
                                                         .value(1)
                                                         .comment("test-1")
                                                         .build();
        AssessmentDto expectedAssessment2 = AssessmentDto.builder()
                                                         .id(2L)
                                                         .entryDto(entryDTO)
                                                         .value(5)
                                                         .comment("test-5")
                                                         .build();

        // Act
        List<AssessmentDto> result = client.get()
                                           .uri(uriBuilder -> uriBuilder.path(URL)
                                                                        .path("/all")
                                                                        .queryParam("entryId", entryId)
                                                                        .queryParam("sort", "id,asc")
                                                                        .build())
                                           .exchange()
                                           .expectStatus()
                                           .isOk()
                                           .expectBodyList(AssessmentDto.class)
                                           .returnResult()
                                           .getResponseBody();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).contains(expectedAssessment1);
        assertThat(result).contains(expectedAssessment2);
    }

    @Test
    @DataSet("assessment\\GET_ALL_FILTERED.json")
    void filteredGetAll() {
        // Arrange
        Long entryId = 1L;
        EntryDTO entryDTO = EntryDTO.builder()
                                    .id(entryId)
                                    .name("test")
                                    .description("test-description")
                                    .links(List.of("test-link"))
                                    .build();
        AssessmentDto expectedAssessment1 = AssessmentDto.builder()
                                                         .id(2L)
                                                         .entryDto(entryDTO)
                                                         .value(5)
                                                         .comment("test-5")
                                                         .build();

        // Act
        List<AssessmentDto> result = client.get()
                                           .uri(uriBuilder -> uriBuilder.path(URL)
                                                                        .path("/all")
                                                                        .queryParam("entryId", entryId)
                                                                        .queryParam("value", 5)
                                                                        .build())
                                           .exchange()
                                           .expectStatus()
                                           .isOk()
                                           .expectBodyList(AssessmentDto.class)
                                           .returnResult()
                                           .getResponseBody();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result).contains(expectedAssessment1);
    }

    @Test
    @DataSet("assessment\\DELETE.json")
    @ExpectedDataSet("assessment\\DELETE_EXPECTED.json")
    void delete() {
        // Act
        client.post()
              .uri(uriBuilder -> uriBuilder.path(URL)
                                           .path("/delete")
                                           .queryParam("id", 1L)
                                           .build())
              .exchange()
              .expectStatus()
              .isOk();
    }
}