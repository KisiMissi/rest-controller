package org.kaoden.ws.homework.repository.audit;

import org.kaoden.ws.homework.model.SecurityAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.InetAddress;

public interface SecurityAuditRepository extends JpaRepository<SecurityAudit, Long> {

    Page<SecurityAudit> findAllByInfo(String info, Pageable pageable);

}
