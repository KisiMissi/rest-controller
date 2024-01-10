package org.kaoden.ws.homework.repository.audit;

import org.kaoden.ws.homework.model.SecurityAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityAuditRepository extends JpaRepository<SecurityAudit, Long> {

    Page<SecurityAudit> findSecurityAuditsByInfoContainingIgnoreCase(String info, Pageable pageable);

}
