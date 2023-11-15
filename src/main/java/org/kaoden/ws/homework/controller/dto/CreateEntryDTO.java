package org.kaoden.ws.homework.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to create Entry")
public class CreateEntryDTO {
    @NotNull(message = "Entry must be named")
    String name;
    @NotNull(message = "Entry must have a description")
    String description;
    @NotNull(message = "Entry must have a link")
    String link;
}
