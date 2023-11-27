package org.kaoden.ws.homework.controller.entry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to update Entry")
public class UpdateEntryDTO {
    @NotBlank(message = "Entry must be named and cannot be blank")
    @Schema(name = "Entry name", requiredMode = REQUIRED)
    String name;
    @NotBlank(message = "Entry must have a description and cannot be blank")
    @Schema(name = "Entry description", requiredMode = REQUIRED)
    String description;
    @NotBlank(message = "Entry must have a link and cannot be blank")
    @Schema(name = "Entry link", requiredMode = REQUIRED)
    String link;
}
