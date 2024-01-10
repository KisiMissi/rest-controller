package org.kaoden.ws.homework.service.audit.argument;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class CreateSecurityAuditArgument {
    Long assessmentId;
    String info;
    LocalDateTime createdAt;
}
