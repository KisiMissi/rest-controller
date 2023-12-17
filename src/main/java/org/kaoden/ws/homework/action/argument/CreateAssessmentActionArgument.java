package org.kaoden.ws.homework.action.argument;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAssessmentActionArgument {
    Long entryId;
    Integer value;
    String comment;
}
