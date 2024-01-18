package org.kaoden.ws.homework.service.entry.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@FieldDefaults(level = PRIVATE)
public class EntriesStatistics {

    Long totalEntries;

    Long entriesAverageValueIs5;
    Double percentageOfEntriesAverageValueIs5;

    Long entriesAverageValueEqualOrHigherThan4;
    Double percentageOfEntriesAverageValueEqualOrHigherThan4;

    Long entriesWithValuesLessThan4;
    Double percentageOfEntriesWithValuesLessThan4;

    Long unratedEntries;

}
