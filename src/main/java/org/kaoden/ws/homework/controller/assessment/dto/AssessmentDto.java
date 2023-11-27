package org.kaoden.ws.homework.controller.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to return assessment of the Entry")
public class AssessmentDto {
    @Schema(name = "Assessment id in storage", requiredMode = REQUIRED)
    Long id;
    @Schema(name = "Id of entry to which assessment is attached", requiredMode = REQUIRED)
    Long entryId;
    @Schema(name = "Assessment value per entry", requiredMode = REQUIRED)
    Integer value;
    @Schema(name = "Assessment comment per entry")
    String comment;
}
