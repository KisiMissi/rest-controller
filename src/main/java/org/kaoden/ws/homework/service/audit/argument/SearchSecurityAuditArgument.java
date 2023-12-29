package org.kaoden.ws.homework.service.audit.argument;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@FieldDefaults(level = PRIVATE)
public class SearchSecurityAuditArgument {

    String info;

}
