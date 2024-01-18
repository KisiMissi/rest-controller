package org.kaoden.ws.homework.service.statistics.argument;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UpdateStatisticsArgument {

    Long totalEntries;
    Long totalAssessments;
    Double averageValue;

    Long entriesAverageValueIs5;
    Double percentageOfEntriesAverageValueIs5;

    Long entriesAverageValueEqualOrHigherThan4;
    Double percentageOfEntriesAverageValueEqualOrHigherThan4;

    Long entriesWithValuesLessThan4;
    Double percentageOfEntriesWithValuesLessThan4;

    Long unratedEntries;

}
