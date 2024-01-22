package org.kaoden.ws.homework.service.statistics;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaoden.ws.homework.model.Statistics;
import org.kaoden.ws.homework.repository.statistics.StatisticsRepository;
import org.kaoden.ws.homework.service.statistics.argument.UpdateStatisticsArgument;
import org.mockito.*;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = PRIVATE)
class StatisticsServiceImplTest {

    @Mock
    StatisticsRepository repository;
    @InjectMocks
    StatisticsServiceImpl service;

    @Captor
    ArgumentCaptor<Statistics> argumentCaptor;

    @BeforeEach
    void setMockitoAnnotations() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getInfoShouldReturnStatistics() {
        // Arrange
        Statistics expected = Statistics.builder()
                                        .id(1L)
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
        when(repository.getFirstBy()).thenReturn(expected);

        // Act
        Statistics actual = service.get();

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateShouldCreateNewStatistics() {
        // Arrange
        UpdateStatisticsArgument argument = UpdateStatisticsArgument.builder()
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
        Statistics expected = Statistics.builder()
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

        // Act
        service.update(argument);

        // Assert
        verify(repository).save(argumentCaptor.capture());
        Statistics actual = argumentCaptor.getValue();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateShouldUpdateExistedStatistics() {
        // Assert
        Statistics testStatistics = Statistics.builder()
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
        UpdateStatisticsArgument argument = UpdateStatisticsArgument.builder()
                                                                    .totalEntries(10L)
                                                                    .totalAssessments(10L)
                                                                    .averageValue(5.0)
                                                                    .entriesAverageValueIs5(5L)
                                                                    .percentageOfEntriesAverageValueIs5(20.0)
                                                                    .entriesAverageValueEqualOrHigherThan4(10L)
                                                                    .percentageOfEntriesAverageValueEqualOrHigherThan4(20.0)
                                                                    .entriesWithValuesLessThan4(10L)
                                                                    .percentageOfEntriesWithValuesLessThan4(20.0)
                                                                    .unratedEntries(10L)
                                                                    .build();
        Statistics expected = Statistics.builder()
                                        .totalEntries(10L)
                                        .totalAssessments(10L)
                                        .averageValue(5.0)
                                        .entriesAverageValueIs5(5L)
                                        .percentageOfEntriesAverageValueIs5(20.0)
                                        .entriesAverageValueEqualOrHigherThan4(10L)
                                        .percentageOfEntriesAverageValueEqualOrHigherThan4(20.0)
                                        .entriesWithValuesLessThan4(10L)
                                        .percentageOfEntriesWithValuesLessThan4(20.0)
                                        .unratedEntries(10L)
                                        .build();
        when(repository.getFirstBy()).thenReturn(testStatistics);

        // Act
        service.update(argument);

        // Assert
        verify(repository).save(argumentCaptor.capture());
        Statistics actual = argumentCaptor.getValue();
        assertThat(actual).isEqualTo(expected);
    }
}