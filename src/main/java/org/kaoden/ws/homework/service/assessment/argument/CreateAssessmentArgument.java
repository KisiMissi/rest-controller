package org.kaoden.ws.homework.service.assessment.argument;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.model.Entry;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAssessmentArgument {
    Entry entry;
    Integer value;
    String comment;
}
