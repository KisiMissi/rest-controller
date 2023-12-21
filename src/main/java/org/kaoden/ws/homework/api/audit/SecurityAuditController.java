package org.kaoden.ws.homework.api.audit;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.api.audit.dto.SearchSecurityAuditDto;
import org.kaoden.ws.homework.api.audit.dto.SecurityAuditDto;
import org.kaoden.ws.homework.api.audit.mapper.SecurityAuditMapper;
import org.kaoden.ws.homework.service.audit.SecurityAuditService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("audits")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Tag(name = "Audit Service", description = "Interaction with audits")
public class SecurityAuditController {

    SecurityAuditService service;
    SecurityAuditMapper mapper;

    @GetMapping("all")
    public List<SecurityAuditDto> getAll(@Valid @RequestBody SearchSecurityAuditDto searchDto,
                                         @PageableDefault Pageable pageable) {
        return mapper.toDtoList(service.getAll(mapper.toModel(searchDto), pageable));
    }

    @PostMapping("delete")
    public void delete(@RequestParam Long id) {
        service.delete(id);
    }
}
