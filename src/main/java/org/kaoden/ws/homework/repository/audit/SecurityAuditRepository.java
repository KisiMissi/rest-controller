package org.kaoden.ws.homework.repository.audit;

import org.kaoden.ws.homework.model.SecurityAudit;
import org.kaoden.ws.homework.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SecurityAuditRepository extends BaseRepository<SecurityAudit, Long> {

    Page<SecurityAudit> findSecurityAuditsByInfoContainingIgnoreCase(String info, Pageable pageable);

}
