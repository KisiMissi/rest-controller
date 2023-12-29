package org.kaoden.ws.homework.api.audit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO to search Security Audit")
public class SearchSecurityAuditDto {

    @Schema(name = "Search by info", requiredMode = REQUIRED)
    String info;

}
