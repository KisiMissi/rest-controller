package org.kaoden.ws.homework.api.assessment.mapper;

import org.kaoden.ws.homework.action.assessments.argument.CreateAssessmentActionArgument;
import org.kaoden.ws.homework.api.assessment.dto.AssessmentDto;
import org.kaoden.ws.homework.api.assessment.dto.CreateAssessmentDto;
import org.kaoden.ws.homework.api.assessment.dto.SearchAssessmentDto;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.assessment.argument.SearchAssessmentArgument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {

    CreateAssessmentActionArgument toModel(CreateAssessmentDto dto);

    SearchAssessmentArgument toModel(SearchAssessmentDto dto);

    @Mapping(target = "entryDto", source = "entry")
    AssessmentDto toDto(EntryAssessment assessment);

    List<AssessmentDto> toDtoList(Page<EntryAssessment> assessmentList);

}
