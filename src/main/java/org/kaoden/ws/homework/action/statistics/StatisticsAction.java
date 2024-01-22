package org.kaoden.ws.homework.action.statistics;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.event.UpdateStatisticsEvent;
import org.kaoden.ws.homework.service.assessment.AssessmentServiceImpl;
import org.kaoden.ws.homework.service.assessment.statistics.AssessmentStatistics;
import org.kaoden.ws.homework.service.entry.EntryServiceImpl;
import org.kaoden.ws.homework.service.entry.statistics.EntriesStatistics;
import org.kaoden.ws.homework.service.statistics.StatisticsService;
import org.kaoden.ws.homework.service.statistics.argument.UpdateStatisticsArgument;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StatisticsAction {

    EntryServiceImpl entryService;
    AssessmentServiceImpl assessmentService;
    StatisticsService statisticsService;

    @Async("statisticsExecutor")
    @EventListener(value = {
            ApplicationReadyEvent.class,
            UpdateStatisticsEvent.class
    })
    public void updateStatistics() {

        EntriesStatistics entriesStat = entryService.getEntriesStatistics();
        AssessmentStatistics assessmentStat = assessmentService.getAssessmentStatistics();

        UpdateStatisticsArgument argument = UpdateStatisticsArgument.builder()
                                                                    .totalEntries(entriesStat.getTotalEntries())
                                                                    .totalAssessments(assessmentStat.getTotalAssessments())
                                                                    .averageValue(assessmentStat.getAverageValue())
                                                                    .entriesAverageValueIs5(entriesStat.getEntriesAverageValueIs5())
                                                                    .percentageOfEntriesAverageValueIs5(entriesStat.getPercentageOfEntriesAverageValueIs5())
                                                                    .entriesAverageValueEqualOrHigherThan4(entriesStat.getEntriesAverageValueEqualOrHigherThan4())
                                                                    .percentageOfEntriesAverageValueEqualOrHigherThan4(entriesStat.getPercentageOfEntriesAverageValueEqualOrHigherThan4())
                                                                    .entriesWithValuesLessThan4(entriesStat.getEntriesWithValuesLessThan4())
                                                                    .percentageOfEntriesWithValuesLessThan4(entriesStat.getPercentageOfEntriesWithValuesLessThan4())
                                                                    .unratedEntries(entriesStat.getUnratedEntries())
                                                                    .build();

        statisticsService.update(argument);
    }
}
