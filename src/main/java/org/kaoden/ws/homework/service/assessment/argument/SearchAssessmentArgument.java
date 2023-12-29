package org.kaoden.ws.homework.service.assessment.argument;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchAssessmentArgument {

    Long entryId;
    Integer value;

}
