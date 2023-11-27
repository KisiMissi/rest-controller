package org.kaoden.ws.homework.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Assessment of the entry")
public class EntryAssessment {
    Long id;
    Long entryId;
    Integer value;
    String comment;
}
