package org.kaoden.ws.homework.controller.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO to search assessments")
public class SearchAssessmentDto {

    @Schema(name = "Id of entry which assessment belongs", requiredMode = REQUIRED)
    @NotNull(message = "To find assessment, entry id is required")
    Long entryId;

    @Schema(name = "Search by assessment value", requiredMode = NOT_REQUIRED)
    Integer value;

}
