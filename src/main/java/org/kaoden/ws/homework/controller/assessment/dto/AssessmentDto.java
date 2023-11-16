package org.kaoden.ws.homework.controller.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to return assessment of the Entry")
public class AssessmentDto {
    Long id;
    Long entryId;
    Integer value;
    String comment;
}
