package org.kaoden.ws.homework.controller.entry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to update Entry")
public class UpdateEntryDTO {
    @NotNull(message = "Entry must be named")
    String name;
    @NotNull(message = "Entry must have a description")
    String description;
    @NotNull(message = "Entry must have a link")
    String link;
}
