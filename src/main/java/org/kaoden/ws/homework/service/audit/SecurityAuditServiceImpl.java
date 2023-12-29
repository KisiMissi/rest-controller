package org.kaoden.ws.homework.service.audit;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.SecurityAudit;
import org.kaoden.ws.homework.repository.audit.SecurityAuditRepository;
import org.kaoden.ws.homework.service.audit.argument.CreateSecurityAuditArgument;
import org.kaoden.ws.homework.service.audit.argument.SearchSecurityAuditArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class SecurityAuditServiceImpl implements SecurityAuditService {

    final SecurityAuditRepository repository;

    @Override
    public SecurityAudit create(CreateSecurityAuditArgument argument) {
        return repository.save(SecurityAudit.builder()
                                            .assessmentId(argument.getAssessmentId())
                                            .info(argument.getInfo())
                                            .createdAt(argument.getCreatedAt())
                                            .build());
    }

    @Override
    public Page<SecurityAudit> getAll(SearchSecurityAuditArgument argument, Pageable pageable) {
        String info = argument.getInfo() == null ? "" : argument.getInfo();
        if (info.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findSecurityAuditsByInfoContainingIgnoreCase(info, pageable);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Security audit with that: " + id + " is not found");

        repository.deleteById(id);
    }
}
