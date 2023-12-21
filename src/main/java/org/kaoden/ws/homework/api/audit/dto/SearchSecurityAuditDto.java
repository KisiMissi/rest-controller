package org.kaoden.ws.homework.api.audit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO to search Security Audit")
public class SearchSecurityAuditDto {

    @NotBlank(message = "Info cannot be blanked")
    @Schema(name = "Search by info", requiredMode = REQUIRED)
    String info;

}
