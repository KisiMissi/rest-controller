package org.kaoden.ws.homework.service.audit;

import org.kaoden.ws.homework.model.SecurityAudit;
import org.kaoden.ws.homework.service.audit.argument.CreateSecurityAuditArgument;
import org.kaoden.ws.homework.service.audit.argument.SearchSecurityAuditArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SecurityAuditService {

    SecurityAudit create(CreateSecurityAuditArgument argument);

    Page<SecurityAudit> getAll(SearchSecurityAuditArgument argument, Pageable pageable);

    void delete(Long id);

}
