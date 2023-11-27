package org.kaoden.ws.homework.service.assessment.argument;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAssessmentArgument {
    Long entryId;
    Integer value;
    String comment;
}
