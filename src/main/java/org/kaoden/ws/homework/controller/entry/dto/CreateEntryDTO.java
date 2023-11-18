package org.kaoden.ws.homework.controller.entry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Entry must be named")
    @Schema(name = "Entry name", requiredMode = REQUIRED)
    String name;
    @NotNull(message = "Entry must have a description")
    @Schema(name = "Entry description", requiredMode = REQUIRED)
    String description;
    @Schema(name = "Entry link", requiredMode = REQUIRED)
    @NotNull(message = "Entry must have a link")
    String link;
}
