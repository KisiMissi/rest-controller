package org.kaoden.ws.homework.api.audit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO to return Security Audit of the Entry Assessment")
public class SecurityAuditDto {
    @Schema(name = "Security audit's id in storage", requiredMode = REQUIRED)
    Long id;
    @Schema(name = "Assessment which security audit is attached", requiredMode = REQUIRED)
    Long assessmentId;
    @Schema(name = "IP address for sending a request to create an assessment", requiredMode = REQUIRED)
    String info;
    @Schema(name = "Date of security audit creation", requiredMode = REQUIRED)
    LocalDateTime createdAt;

}
