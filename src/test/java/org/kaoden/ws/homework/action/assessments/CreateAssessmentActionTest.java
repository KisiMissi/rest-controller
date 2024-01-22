package org.kaoden.ws.homework.action.assessments;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.action.assessments.argument.CreateAssessmentActionArgument;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.AssessmentService;
import org.kaoden.ws.homework.service.assessment.argument.CreateAssessmentArgument;
import org.kaoden.ws.homework.service.entry.EntryService;
import org.mockito.*;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@FieldDefaults(level = PRIVATE)
class CreateAssessmentActionTest {

    @Mock
    EntryService entryService;
    @Mock
    AssessmentService assessmentService;
    @InjectMocks
    CreateAssessmentAction assessmentAction;

    @Captor
    ArgumentCaptor<EntryAssessment> argumentCaptor;

    @BeforeEach
    void setMockitoAnnotations() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEntryAssessment() {
        // Arrange
        Long testEntryId = 1L;
        Integer testValue = 1;
        String testComment = "test-comment";
        Entry testEntry = Mockito.mock(Entry.class);
        CreateAssessmentActionArgument actionArgument = CreateAssessmentActionArgument.builder()
                                                                                      .entryId(testEntryId)
                                                                                      .value(testValue)
                                                                                      .comment(testComment)
                                                                                      .build();
        CreateAssessmentArgument assessmentArgument = CreateAssessmentArgument.builder()
                                                                              .entry(testEntry)
                                                                              .value(testValue)
                                                                              .comment(testComment)
                                                                              .build();
        EntryAssessment expectedEntryAssessment = EntryAssessment.builder()
                                                                 .entry(testEntry)
                                                                 .value(testValue)
                                                                 .comment(testComment)
                                                                 .build();

        when(entryService.exists(testEntryId)).thenReturn(true);
        when(entryService.getExisting(testEntryId)).thenReturn(testEntry);
        when(assessmentService.create(testEntry, assessmentArgument)).thenReturn(expectedEntryAssessment);

        // Act
        EntryAssessment actualAssessment = assessmentAction.addAssessment(actionArgument);

        // Assert
        assertThat(actualAssessment).isEqualTo(expectedEntryAssessment);
    }

    @Test
    void addAssessmentShouldThrowException() {
        // Arrange
        Long testEntryId = 1L;
        CreateAssessmentActionArgument argument = CreateAssessmentActionArgument.builder()
                                                                                .entryId(testEntryId)
                                                                                .build();

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> assessmentAction.addAssessment(argument));

        // Assert
        assertThat(exception.getMessage()).isEqualTo("Entry with that ID: " + testEntryId + " doesn't exist");
    }
}