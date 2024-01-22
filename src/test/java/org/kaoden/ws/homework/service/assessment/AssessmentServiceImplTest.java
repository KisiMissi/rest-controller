package org.kaoden.ws.homework.service.assessment;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.repository.assessment.AssessmentRepository;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.kaoden.ws.homework.service.assessment.argument.SearchAssessmentArgument;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = PRIVATE)
class AssessmentServiceImplTest {

    final Entry testEntry = Entry.builder()
                                 .id(1L)
                                 .name("test-entry")
                                 .description("test-description")
                                 .links(List.of("link"))
                                 .build();

    @Mock
    Pageable pageable;
    @Mock
    AssessmentRepository repository;
    @InjectMocks
    AssessmentServiceImpl service;

    @Captor
    ArgumentCaptor<EntryAssessment> argumentCaptor;

    @BeforeEach
    void setMockitoAnnotations() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAssessment() {
        // Arrange
        Integer testValue = 5;
        String testComment = "test-comment";
        CreateAssessmentArgument argument = CreateAssessmentArgument.builder()
                                                                    .entry(testEntry)
                                                                    .value(testValue)
                                                                    .comment(testComment)
                                                                    .build();

        // Act
        service.create(testEntry, argument);

        // Assert
        verify(repository).save(argumentCaptor.capture());
        EntryAssessment actual = argumentCaptor.getValue();
        assertThat(actual.getEntry()).isEqualTo(testEntry);
        assertThat(actual.getValue()).isEqualTo(testValue);
        assertThat(actual.getComment()).isEqualTo(testComment);
    }

    @Test
    void getAllAssessmentsByValue() {
        // Arrange
        Integer testValue = 1;
        SearchAssessmentArgument argument = SearchAssessmentArgument.builder()
                                                                    .entryId(testEntry.getId())
                                                                    .value(testValue)
                                                                    .build();
        EntryAssessment assessment = EntryAssessment.builder()
                                                    .entry(testEntry)
                                                    .value(testValue)
                                                    .comment("value-one")
                                                    .build();
        when(repository.findEntryAssessmentByEntryIdAndValue(testEntry.getId(), testValue, pageable)).thenReturn(new PageImpl<>(List.of(assessment)));

        // Act
        List<EntryAssessment> actualAssessments = service.getAll(argument, pageable)
                                                         .toList();

        // Assert
        assertThat(actualAssessments).isEqualTo(List.of(assessment));
    }

    @Test
    void getAllAssessmentsByEntryId() {
        // Arrange
        Integer testValue = 5;
        SearchAssessmentArgument argument = SearchAssessmentArgument.builder()
                                                                    .entryId(testEntry.getId())
                                                                    .build();
        EntryAssessment assessment = EntryAssessment.builder()
                                                    .entry(testEntry)
                                                    .value(testValue)
                                                    .comment("value-five")
                                                    .build();
        when(repository.findEntryAssessmentByEntryId(testEntry.getId(), pageable)).thenReturn(new PageImpl<>(List.of(assessment)));

        // Act
        List<EntryAssessment> actualAssessments = service.getAll(argument, pageable)
                                                         .toList();

        // Assert
        assertThat(actualAssessments).isEqualTo(List.of(assessment));
    }

    @Test
    void delete() {
        // Act
        service.delete(1L);

        // Assert
        verify(repository).deleteById(1L);

    }

}