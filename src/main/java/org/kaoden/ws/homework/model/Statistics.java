package org.kaoden.ws.homework.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Statistics {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

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
