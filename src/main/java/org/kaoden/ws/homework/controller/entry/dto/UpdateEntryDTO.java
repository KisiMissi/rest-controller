package org.kaoden.ws.homework.controller.entry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO to update Entry")
public class UpdateEntryDTO {
    @Schema(name = "Entry name", requiredMode = REQUIRED)
    @NotBlank(message = "Entry must be named and cannot be blank")
    String name;

    @Schema(name = "Entry description", requiredMode = REQUIRED)
    @NotBlank(message = "Entry must have a description and cannot be blank")
    String description;

    @Schema(name = "Entry link", requiredMode = REQUIRED)
    @NotNull @Size(min = 1, message = "Entry must have at least one link")
    List<@NotBlank(message = "Link cannot be blanked") @Size(max = 255) String> links;
}
