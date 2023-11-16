package org.kaoden.ws.homework.service.assessment.argument;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssessmentArgument {
    Long entryId;
    Integer value;
    String comment;
}
