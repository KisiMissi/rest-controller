package org.kaoden.ws.homework.controller.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to create assessment of the Entry")
public class CreateAssessmentDto {
    @NotNull(message = "Assessment must have value")
    @Min(value = 1, message = "Value must be no less than 1")
    @Max(value = 5, message = "Value must be no more than 5")
    Integer value;

    @NotBlank(message = "Assessment must have a comment and it cannot be blank")
    String comment;
}
