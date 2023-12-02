package org.kaoden.ws.homework.controller.entry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotBlank(message = "Entry must be named and cannot be blank")
    @Schema(name = "Entry name", requiredMode = REQUIRED)
    String name;

    @NotBlank(message = "Entry must have a description and cannot be blank")
    @Schema(name = "Entry description", requiredMode = REQUIRED)
    String description;

    @Schema(name = "Entry link", requiredMode = REQUIRED)
    @NotEmpty(message = "Entry must have at least one link")
    List<@NotBlank(message = "Link cannot be blanked") String> links;
}
