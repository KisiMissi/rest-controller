package org.kaoden.ws.homework.action.statistics;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.service.assessment.AssessmentServiceImpl;
import org.kaoden.ws.homework.service.assessment.statistics.AssessmentStatistics;
import org.kaoden.ws.homework.service.entry.EntryServiceImpl;
import org.kaoden.ws.homework.service.entry.statistics.EntriesStatistics;
import org.kaoden.ws.homework.service.statistics.StatisticsServiceImpl;
import org.kaoden.ws.homework.service.statistics.argument.UpdateStatisticsArgument;
import org.mockito.*;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = PRIVATE)
class StatisticsActionTest {

    @Mock
    EntryServiceImpl entryService;
    @Mock
    AssessmentServiceImpl assessmentService;
    @Mock
    StatisticsServiceImpl statisticsService;

    @InjectMocks
    StatisticsAction statisticsAction;

    @Captor
    ArgumentCaptor<UpdateStatisticsArgument> argumentCaptor;

    @BeforeEach
    void setMockitoAnnotations() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateStatistics() {
        // Arrange
        EntriesStatistics entriesStatistics = EntriesStatistics.builder()
                                                               .totalEntries(1L)
                                                               .entriesAverageValueIs5(1L)
                                                               .percentageOfEntriesAverageValueIs5(1.0)
                                                               .entriesAverageValueEqualOrHigherThan4(1L)
                                                               .percentageOfEntriesAverageValueEqualOrHigherThan4(1.0)
                                                               .entriesWithValuesLessThan4(1L)
                                                               .percentageOfEntriesWithValuesLessThan4(1.0)
                                                               .unratedEntries(1L)
                                                               .build();
        AssessmentStatistics assessmentStatistics = AssessmentStatistics.builder()
                                                                        .totalAssessments(1L)
                                                                        .averageValue(1.0)
                                                                        .build();

        UpdateStatisticsArgument expectedUpdateStatisticsArgument = UpdateStatisticsArgument.builder()
                                                                                            .totalEntries(1L)
                                                                                            .totalAssessments(1L)
                                                                                            .averageValue(1.0)
                                                                                            .entriesAverageValueIs5(1L)
                                                                                            .percentageOfEntriesAverageValueIs5(1.0)
                                                                                            .entriesAverageValueEqualOrHigherThan4(1L)
                                                                                            .percentageOfEntriesAverageValueEqualOrHigherThan4(1.0)
                                                                                            .entriesWithValuesLessThan4(1L)
                                                                                            .percentageOfEntriesWithValuesLessThan4(1.0)
                                                                                            .unratedEntries(1L)
                                                                                            .build();

        when(entryService.getEntriesStatistics()).thenReturn(entriesStatistics);
        when(assessmentService.getAssessmentStatistics()).thenReturn(assessmentStatistics);

        // Act
        statisticsAction.updateStatistics();

        // Assert
        verify(statisticsService).update(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).usingRecursiveComparison()
                          .isEqualTo(expectedUpdateStatisticsArgument);
    }

}