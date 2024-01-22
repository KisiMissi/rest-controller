package org.kaoden.ws.homework.service.assessment.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@FieldDefaults(level = PRIVATE)
public class AssessmentStatistics {

    Long totalAssessments;
    Double averageValue;

}
