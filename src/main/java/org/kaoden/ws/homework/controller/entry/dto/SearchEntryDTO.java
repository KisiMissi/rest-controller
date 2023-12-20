package org.kaoden.ws.homework.controller.entry.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO to search entries")
public class SearchEntryDTO {

    @Schema(name = "Search name", requiredMode = NOT_REQUIRED)
    String name;

    @Schema(name = "Search description", requiredMode = NOT_REQUIRED)
    String description;

}
