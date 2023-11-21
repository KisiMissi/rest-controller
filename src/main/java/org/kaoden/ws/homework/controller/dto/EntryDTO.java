package org.kaoden.ws.homework.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to return Entry")
public class EntryDTO {
    @Schema(name = "Entry ID in storage", requiredMode = REQUIRED)
    Long id;
    @Schema(name = "Entry name", requiredMode = REQUIRED)
    String name;
    @Schema(name = "Entry description")
    String description;
    @Schema(name = "Entry link")
    String link;
}