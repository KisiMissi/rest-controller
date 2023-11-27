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
@Schema(description = "DTO to create Entry")
public class CreateEntryDTO {
    @NotBlank(message = "Entry must be named and it cannot be blank")
    @Schema(name = "Entry name", requiredMode = REQUIRED)
    String name;
    @NotBlank(message = "Entry must have a description and it cannot be blank")
    @Schema(name = "Entry description", requiredMode = REQUIRED)
    String description;
    @Schema(name = "Entry link", requiredMode = REQUIRED)
    @NotBlank(message = "Entry must have a link and it cannot be blank")
    String link;
}
