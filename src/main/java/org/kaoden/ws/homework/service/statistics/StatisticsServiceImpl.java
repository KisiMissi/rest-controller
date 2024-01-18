package org.kaoden.ws.homework.service.statistics;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.model.Statistics;
import org.kaoden.ws.homework.repository.statistics.StatisticsRepository;
import org.kaoden.ws.homework.service.statistics.argument.UpdateStatisticsArgument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class StatisticsServiceImpl implements StatisticsService {

    final StatisticsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Statistics get() {
        return repository.getFirstBy();
    }

    @Override
    @Transactional
    public void update(UpdateStatisticsArgument argument) {
        Statistics statistics = Optional.ofNullable(repository.getFirstBy())
                                        .orElse(new Statistics());

        statistics.setTotalEntries(argument.getTotalEntries());
        statistics.setTotalAssessments(argument.getTotalAssessments());
        statistics.setAverageValue(argument.getAverageValue());

        statistics.setEntriesAverageValueIs5(argument.getEntriesAverageValueIs5());
        statistics.setPercentageOfEntriesAverageValueIs5(argument.getPercentageOfEntriesAverageValueIs5());

        statistics.setEntriesAverageValueEqualOrHigherThan4(argument.getEntriesAverageValueEqualOrHigherThan4());
        statistics.setPercentageOfEntriesAverageValueEqualOrHigherThan4(argument.getPercentageOfEntriesAverageValueEqualOrHigherThan4());

        statistics.setEntriesWithValuesLessThan4(argument.getEntriesWithValuesLessThan4());
        statistics.setPercentageOfEntriesWithValuesLessThan4(argument.getPercentageOfEntriesWithValuesLessThan4());

        statistics.setUnratedEntries(argument.getUnratedEntries());

        repository.save(statistics);
    }
}
