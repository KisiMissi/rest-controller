package org.kaoden.ws.homework.api.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Schema(description = "DTO to return statistics of the Utility Storage")
public class StatisticsDto {

    @Schema(name = "Total number of Entries")
    Long totalEntries;
    @Schema(name = "Total number of Assessments")
    Long totalAssessments;
    @Schema(name = "Average value of Assessments")
    Float averageValue;

    @Schema(name = "Number of entries with average value 5")
    Long entriesAverageValueIs5;
    @Schema(name = "Percentage of entries with average value 5")
    Float percentageOfEntriesAverageValueIs5;

    @Schema(name = "Number of entries with average number 4 or higher")
    Long entriesAverageValueEqualOrHigherThan4;
    @Schema(name = "Percentage of entries with average number 4 or higher")
    Float percentageOfEntriesAverageValueEqualOrHigherThan4;

    @Schema(name = "Number of entries with values less then 4")
    Long entriesWithValuesLessThan4;
    @Schema(name = "Percentage of entries with values less then 4")
    Float percentageOfEntriesWithValuesLessThan4;

    @Schema(name = "Number of unrated entries")
    Long unratedEntries;

}
