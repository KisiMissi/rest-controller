package org.kaoden.ws.homework.controller.assessment.mapper;

import org.kaoden.ws.homework.action.argument.CreateAssessmentActionArgument;
import org.kaoden.ws.homework.controller.assessment.dto.AssessmentDto;
import org.kaoden.ws.homework.controller.assessment.dto.CreateAssessmentDto;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {

    CreateAssessmentActionArgument toModel(CreateAssessmentDto dto);

    AssessmentDto toDto(EntryAssessment assessment);

    List<AssessmentDto> toDtoList(Page<EntryAssessment> assessmentList);

}
