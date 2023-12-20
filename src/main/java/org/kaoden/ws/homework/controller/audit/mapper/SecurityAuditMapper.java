package org.kaoden.ws.homework.controller.audit.mapper;

import org.kaoden.ws.homework.controller.audit.dto.SearchSecurityAuditDto;
import org.kaoden.ws.homework.controller.audit.dto.SecurityAuditDto;
import org.kaoden.ws.homework.model.SecurityAudit;
import org.kaoden.ws.homework.service.audit.argument.SearchSecurityAuditArgument;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SecurityAuditMapper {

    List<SecurityAuditDto> toDtoList(Page<SecurityAudit> securityAudits);

    SearchSecurityAuditArgument toModel(SearchSecurityAuditDto searchSecurityAuditDto);

}
