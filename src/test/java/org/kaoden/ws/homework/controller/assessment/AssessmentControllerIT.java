package org.kaoden.ws.homework.controller.assessment;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.controller.assessment.dto.AssessmentDto;
import org.kaoden.ws.homework.controller.assessment.dto.CreateAssessmentDto;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.assessment.AssessmentRepository;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@AutoConfigureWebTestClient
@FieldDefaults(level = PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssessmentControllerIT {

    static final String URL = "assessment";

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    WebTestClient client;
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    AssessmentRepository assessmentRepository;

    @Test
    void create() {
        // Arrange
        Entry savedEntry = entryRepository.save(Entry.builder()
                                                     .build());
        Long entryId = savedEntry.getId();
        Integer assessmentValue = 1;
        String assessmentComment = "test-1";
        CreateAssessmentDto testAssessment = CreateAssessmentDto.builder()
                                                                .value(assessmentValue)
                                                                .comment(assessmentComment)
                                                                .build();

        // Act
        EntityExchangeResult<AssessmentDto> result = client.post()
                                                           .uri(uriBuilder -> uriBuilder.path(URL)
                                                                                        .path("/create")
                                                                                        .queryParam("entryId", entryId)
                                                                                        .build())
                                                           .bodyValue(testAssessment)
                                                           .exchange()
                                                           .expectStatus()
                                                           .isCreated()
                                                           .expectBody(AssessmentDto.class)
                                                           .returnResult();

        // Assert
        AssessmentDto actualAssessment = result.getResponseBody();
        assertThat(actualAssessment.getEntry()
                                   .getId()).isEqualTo(savedEntry.getId());
        assertThat(actualAssessment.getValue()).isEqualTo(assessmentValue);
        assertThat(actualAssessment.getComment()).isEqualTo(assessmentComment);
    }

    @Test
    void getAll() {
        // Arrange
        Entry savedEntry = entryRepository.save(Entry.builder()
                                                     .links(List.of())
                                                     .build());
        EntryAssessment savedAssessment1 = assessmentRepository.save(EntryAssessment.builder()
                                                                                    .entry(savedEntry)
                                                                                    .value(1)
                                                                                    .comment("test-1")
                                                                                    .build());
        EntryAssessment savedAssessment2 = assessmentRepository.save(EntryAssessment.builder()
                                                                                    .entry(savedEntry)
                                                                                    .value(5)
                                                                                    .comment("test-5")
                                                                                    .build());
        AssessmentDto expectedAssessment1 = AssessmentDto.builder()
                                                         .id(savedAssessment1.getId())
                                                         .entry(savedEntry)
                                                         .value(1)
                                                         .comment("test-1")
                                                         .build();
        AssessmentDto expectedAssessment2 = AssessmentDto.builder()
                                                         .id(savedAssessment2.getId())
                                                         .entry(savedEntry)
                                                         .value(5)
                                                         .comment("test-5")
                                                         .build();
        List<AssessmentDto> expectedAssessmentList = List.of(expectedAssessment1, expectedAssessment2);

        // Act
        EntityExchangeResult<List<AssessmentDto>> result = client.get()
                                                                 .uri(uriBuilder -> uriBuilder.path(URL)
                                                                                              .path("/all")
                                                                                              .queryParam("entryId", savedEntry.getId())
                                                                                              .queryParam("sort", "id,asc")
                                                                                              .build())
                                                                 .exchange()
                                                                 .expectStatus()
                                                                 .isOk()
                                                                 .expectBodyList(AssessmentDto.class)
                                                                 .returnResult();

        // Assert
        assertThat(result.getResponseBody()).isEqualTo(expectedAssessmentList);
    }

    @Test
    void filteredGetAll() {
        // Arrange
        Entry savedEntry = entryRepository.save(Entry.builder()
                                                     .links(List.of())
                                                     .build());
        assessmentRepository.save(EntryAssessment.builder()
                                                 .entry(savedEntry)
                                                 .value(1)
                                                 .comment("test-1")
                                                 .build());
        EntryAssessment savedAssessment2 = assessmentRepository.save(EntryAssessment.builder()
                                                                                    .entry(savedEntry)
                                                                                    .value(5)
                                                                                    .comment("test-5")
                                                                                    .build());
        List<AssessmentDto> expectedList = List.of(AssessmentDto.builder()
                                                                .id(savedAssessment2.getId())
                                                                .entry(savedEntry)
                                                                .value(5)
                                                                .comment("test-5")
                                                                .build());

        // Act
        EntityExchangeResult<List<AssessmentDto>> result = client.get()
                                                                 .uri(uriBuilder -> uriBuilder.path(URL)
                                                                                              .path("/all")
                                                                                              .queryParam("entryId", savedEntry.getId())
                                                                                              .queryParam("value", 5)
                                                                                              .build())
                                                                 .exchange()
                                                                 .expectStatus()
                                                                 .isOk()
                                                                 .expectBodyList(AssessmentDto.class)
                                                                 .returnResult();

        // Assert
        assertThat(result.getResponseBody()).isEqualTo(expectedList);
    }

    @Test
    void delete() {
        // Arrange
        Pageable pageable = Mockito.mock(Pageable.class);
        Entry saveEntry = entryRepository.save(Entry.builder()
                                                    .build());
        EntryAssessment savedAssessment = assessmentRepository.save(EntryAssessment.builder()
                                                                                   .entry(saveEntry)
                                                                                   .value(1)
                                                                                   .comment("test-1")
                                                                                   .build());
        Long assessmentId = savedAssessment.getId();

        // Act
        client.post()
              .uri(uriBuilder -> uriBuilder.path(URL)
                                           .path("/delete")
                                           .queryParam("id", assessmentId)
                                           .build())
              .exchange()
              .expectStatus()
              .isOk();

        // Assert
        assertThat(assessmentRepository.findById(assessmentId)).isEmpty();
    }
}